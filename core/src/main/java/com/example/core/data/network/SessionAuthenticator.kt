package com.example.core.data.network

import com.example.core.data.interceptor.TokenInterceptor
import com.example.core.domain.feature.SessionTokenRefresher
import dagger.Lazy
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
    val tokenInterceptor: TokenInterceptor
) : Authenticator {


    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.authRetryCount >= MAX_AUTH_RETRY_COUNT) {
            return null
        }

        response.retryWithLatestTokenIfChanged()?.let { return it }

        val refreshed = runBlocking {
            sessionTokenRefresher.get().refreshIfPossible()
        }

        if (refreshed.not()) return null

        return response.request.withLatestAuthorization()
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


