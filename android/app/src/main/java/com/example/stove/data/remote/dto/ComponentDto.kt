package com.example.stove.data.remote.dto

import kotlinx.serialization.SerialName

data class ComponentDto(
    val id: Int,
    val name: String,
    val description: String,
    @SerialName("is_required") val isRequired: Boolean,
    @SerialName("allow_multiple_choices") val allowMultipleChoices: Boolean,
    @SerialName("component_options") val componentOptions: Boolean
)
