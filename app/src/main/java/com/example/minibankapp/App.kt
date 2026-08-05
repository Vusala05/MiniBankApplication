package com.example.minibankapp

import android.app.Application
import com.example.feature_auth.data.dataSource.AuthLocalDataSource
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var authLocalDataSource: AuthLocalDataSource

    override fun onCreate() {
        super.onCreate()

        runBlocking {
            if (authLocalDataSource.getRefreshToken().isBlank()) {
              authLocalDataSource.saveRefreshToken("Test Token")
            }
        }
    }
}