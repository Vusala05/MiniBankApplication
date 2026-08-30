/*
package com.example.feature_auth.data.module

import com.example.core.data.interceptor.TokenInterceptor
import com.example.core.data.module.NetworkModule
import com.example.feature_auth.data.helper.SessionAuthenticator
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AuthNetworkModule {

    @Provides
    @Singleton
    @Named("Client")
    fun provideMainOkHttpClient(
        @Named("Main-interceptor") loggingInterceptor: HttpLoggingInterceptor,   // <-- düzəliş
        tokenInterceptor: TokenInterceptor,
        sessionAuthenticator: SessionAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .authenticator(sessionAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    @Named("Retrofit")
    fun provideRetrofit(
        @Named("Client") okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9090/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}*/
