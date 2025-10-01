package com.example.stove.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewConfigurationResponse(
    val id: Int
)
