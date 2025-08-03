package com.example.stove.data.remote.dto

import kotlinx.serialization.SerialName

data class OptionDto(
    val id: Int,
    val name: String,
    @SerialName("price_modifier") val priceModifier: Int,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("is_default") val isDefault: Boolean
)
