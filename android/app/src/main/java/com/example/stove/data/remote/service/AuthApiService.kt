package com.example.stove.data.remote.service

import com.example.stove.data.dto.AuthResponse
import com.example.stove.data.dto.LoginRequest
import com.example.stove.data.dto.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest) : AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest) : AuthResponse
}