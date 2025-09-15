package com.example.stove.domain.dto.designer


data class Component(
    val id: Int,
    val name: String,
    val description: String,
    val isRequired: Boolean,
    val allowMultipleChoices: Boolean,
    val componentOptions: Boolean
)

