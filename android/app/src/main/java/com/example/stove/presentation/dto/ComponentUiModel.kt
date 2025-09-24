package com.example.stove.presentation.dto

data class ComponentUiModel(
    val id: Int,
    val name: String,
    val description: String,
    val isRequired: Boolean,
    val allowMultipleChoices: Boolean,
    val componentOptions: Boolean
)
