package com.example.stove.domain.repository

import com.example.stove.core.Resource
import com.example.stove.data.dto.LoginRequest
import com.example.stove.data.dto.RegisterRequest

interface AuthRepository {
    suspend fun register(request: RegisterRequest) : Resource<Unit>

    suspend fun login(request: LoginRequest) : Resource<Unit>
}