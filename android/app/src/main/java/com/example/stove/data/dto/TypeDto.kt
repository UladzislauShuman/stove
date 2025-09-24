package com.example.stove.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class TypeDto (
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("base_price") val basePrice: Int?,
    @SerializedName("image_url") val imageUrl: String?
)