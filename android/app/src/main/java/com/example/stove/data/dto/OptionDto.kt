package com.example.stove.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class OptionDto(
    val id: Int,
    val name: String,
    @SerializedName("price_modifier") val priceModifier: Int?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("is_default") val isDefault: Boolean?
)
