package com.example.stove.domain.model.designer

import com.example.stove.presentation.dto.ComponentOption

data class Configuration(
    val typeId: Int?,
    val optionIds: List<Int>,
    val addonIds: List<Int>,
    val draftName: String
)
