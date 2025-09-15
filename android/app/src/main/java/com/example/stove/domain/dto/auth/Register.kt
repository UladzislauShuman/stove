package com.example.stove.domain.dto.auth

data class Register(
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val password: String
)