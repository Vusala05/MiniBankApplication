package com.example.feature_auth

import com.example.feature_auth.domain.useCases.ValidatePinUseCase
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ValidatePinUseCaseTest {

    lateinit var useCase : ValidatePinUseCase

    @Before
    fun setUp() {
        useCase = ValidatePinUseCase()
    }

    @Test
    fun `Short pin fails`() {
        assertFalse(useCase.validate("123"))
    }

    @Test
    fun `Pin with length 4 succeeds`() {
        assertTrue(useCase.validate("1234"))
    }

}