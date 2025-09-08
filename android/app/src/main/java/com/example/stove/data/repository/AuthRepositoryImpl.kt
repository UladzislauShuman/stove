package com.example.stove.data.repository

import androidx.security.crypto.EncryptedSharedPreferences
import com.example.stove.core.Resource
import com.example.stove.data.dto.LoginRequest
import com.example.stove.data.dto.RegisterRequest
import com.example.stove.data.remote.service.AuthApiService
import com.example.stove.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthApiService,
    private val secretPreferences: EncryptedSharedPreferences
) : AuthRepository {
    override suspend fun login(request: LoginRequest): Resource<Unit> {
        try {
            val response = authService.login(request)

            secretPreferences.edit()
                .putString("JWT_TOKEN", response.token)
                .putBoolean("IS_LOGGED_IN", true)
                .apply()
            return Resource.SUCCESS(Unit)
        } catch(e: Exception) {
            return Resource.FAILURE(e)
        }
    }

    override suspend fun register(request: RegisterRequest): Resource<Unit> {
        TODO("Not yet implemented")
    }

}