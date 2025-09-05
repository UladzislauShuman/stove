package com.example.stove.domain.model.auth

import com.google.gson.annotations.SerializedName

data class Register(
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val password: String
)