package com.example.minibankapp

import android.app.Application
import com.example.feature_auth.data.dataSource.AuthLocalDataStore
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var authLocalDataStore: AuthLocalDataStore

    override fun onCreate() {
        super.onCreate()

        runBlocking {
            if (authLocalDataStore.getRefreshToken().isBlank()) {
                authLocalDataStore.saveRefreshToken("Test Token")
            }
        }
    }
}