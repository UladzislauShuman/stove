package com.example.stove.data.remote.dto

import kotlinx.serialization.SerialName

data class TypeDto (
    val id: Int,
    val name: String,
    val description: String,
    @SerialName("base_price") val basePrice: Int,
    @SerialName("image_url") val imageUrl: String
)