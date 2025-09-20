package com.example.stove.domain.usecase.auth

import com.example.stove.data.util.AuthenticationManager
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationManager: AuthenticationManager
) {
    fun invoke() {
        authenticationManager.logout()
    }
}