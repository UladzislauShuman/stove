package com.example.stove.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.stove.core.Resource
import com.example.stove.data.di.NetworkModule
import com.example.stove.data.dto.LoginRequest
import com.example.stove.data.dto.RegisterRequest
import com.example.stove.data.local.TokenManager
import com.example.stove.data.remote.service.AuthApiService
import com.example.stove.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okio.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {
    override suspend fun login(request: LoginRequest): Resource<Unit> {
        try {
            val response = authService.login(request)

            if(response.isSuccessful) {
                if(!response.body()?.token.isNullOrEmpty()) {
                    tokenManager.putToken(response.body()?.token)
                    Log.i("AuthRepository", "Login success")
                    return Resource.SUCCESS(Unit)
                } else {
                    return Resource.FAILURE(Throwable("Authentification error."))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error."
                // !!! Так делать не хорошо, надо потом сделать класс ApiError
                return Resource.FAILURE(Throwable(errorBody))
            }

        } catch(e: HttpException) {
            return Resource.FAILURE(Throwable(e.message))
        } catch(e: IOException) {
            return Resource.FAILURE(Throwable("Network error. Check your connection."))
        }
    }

    override suspend fun register(request: RegisterRequest): Resource<Unit> {
        try {
            val response = authService.register(request)

            if(response.isSuccessful) {
                if(!response.body()?.token.isNullOrEmpty()) {
                    tokenManager.putToken(response.body()?.token)
                    Log.i("AuthRepository", "Register success")
                    return Resource.SUCCESS(Unit)
                } else {
                    Log.e("AuthRepository", "Auth error")
                    return Resource.FAILURE(Throwable("Authentification error."))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error."
                Log.e("AuthRepository", "Error from server: $errorBody")
                // !!! Так делать не хорошо, надо потом сделать класс ApiError
                return Resource.FAILURE(Throwable(errorBody))
            }

        } catch(e: HttpException) {
            Log.e("AuthRepository", e.message ?: "Http error")
            return Resource.FAILURE(Throwable(e.message))
        } catch(e: IOException) {
            Log.e("AuthRepository", e.message ?: "IO error.")
            return Resource.FAILURE(Throwable("Network error. Check your connection."))
        }
    }
}