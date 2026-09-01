/*
package com.example.feature_auth

import com.example.feature_auth.data.dataSource.AuthLocalDataSource
import com.example.feature_auth.domain.useCases.SubmitPinUseCase
import org.junit.Before
import org.junit.Test

class SubmitPinUseCaseTest {

    @MockK
    lateinit var authLocalDataSource: AuthLocalDataSource

    @Test
    fun `Submit use case fails when token is not received`() {

        coEvery { authLocalDataSource.saveAccessToken(any<String>()) } returns Unit
        coEvery { authLocalDataSource.saveRefreshToken(any<String>()) } returns Unit

        val useCase = SubmitPinUseCase(
            authLocalDataSource, .. , ..
        )

    }
}*/
