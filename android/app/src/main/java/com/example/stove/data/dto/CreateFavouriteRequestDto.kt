package com.example.stove.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateFavouriteRequestDto(
    @Json(name = "stove_type_id")
    val stoveTypeId: String,
    val name: String,
    val choices: List<OptionIdDto>,
    val addons: List<AddonIdDto>
)
