package com.example.feature_auth.data.helper

import android.content.Context
import android.content.Intent
import com.example.core.data.interceptor.TokenInterceptor
import com.example.core.domain.feature.PinFlowChannel
import com.example.core.domain.feature.RefreshedResult
import com.example.core.domain.feature.SessionTokenRefresher
import com.example.feature_auth.ui.PinActivity
import com.example.feature_auth.ui.util.PinStep
import dagger.Lazy
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionAuthenticator @Inject constructor(
    val sessionTokenRefresher: Lazy<SessionTokenRefresher>,
    val tokenInterceptor: TokenInterceptor,
    val pinFlowChannel: PinFlowChannel,
    @ApplicationContext val context: Context
) : Authenticator {


    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.authRetryCount >= MAX_AUTH_RETRY_COUNT) {
            return null
        }
        synchronized(this) {
            response.retryWithLatestTokenIfChanged()?.let { return it }

            val refreshed = runBlocking {
                sessionTokenRefresher.get().refreshIfPossible()
            }

            if (refreshed !is RefreshedResult.Success) return null

            if (refreshed.requiredPinSet) {
                if (!pinFlowChannel.hasPendingFlow()) {
                    val intent = Intent(context, PinActivity::class.java).apply {
                        putExtra("PIN_STEP", PinStep.PIN_VERIFIED.name )
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)

                }

                val pinSuccess = runBlocking {
                    pinFlowChannel.awaitPinResult()
                }

                if (!pinSuccess) return null
            }

            return response.request.withLatestAuthorization()
        }
    }

    private fun Response.retryWithLatestTokenIfChanged(): Request? {
        val latestAccessToken = tokenInterceptor.accessToken
        if (latestAccessToken.isBlank() || request.authorizationToken() == latestAccessToken) {
            return null
        }

        return request.withLatestAuthorization()
    }

    private fun Request.withLatestAuthorization(): Request? {
        val accessToken = tokenInterceptor.accessToken
        if (accessToken.isBlank()) return null

        return newBuilder()
            .header(AUTHORIZATION, "Bearer $accessToken")
            .build()
    }

    private fun Request.authorizationToken(): String? {
        val authorization = header(AUTHORIZATION) ?: return null
        return authorization.substringAfter(" ", missingDelimiterValue = authorization)
    }

    private val Response.authRetryCount: Int
        get() {
            var response: Response? = this
            var count = 0
            while (response?.priorResponse != null) {
                count++
                response = response.priorResponse
            }
            return count
        }

    private companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val MAX_AUTH_RETRY_COUNT = 1
    }
    }