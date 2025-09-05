package com.example.stove.domain.repository

import com.example.stove.data.remote.service.AuthApiService

interface AuthRepository {
    suspend fun register()
}