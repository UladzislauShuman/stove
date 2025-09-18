package com.example.stove.domain.repository

import com.example.stove.core.Resource
import com.example.stove.domain.dto.profile.UserInfo

interface ProfileRepository {
    suspend fun getUserInfo() : Resource<UserInfo>
}