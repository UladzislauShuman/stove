package com.example.stove.model

import com.example.stove.R

data object StoveProperties {
    val types =
        listOf(
            StoveCharacteristics(id = 1, nameRes = R.string.caption_fireplace, imageRes = R.drawable.fireplace_card),
            StoveCharacteristics(id = 2, nameRes = R.string.caption_stove, imageRes = R.drawable.stove_card),
            StoveCharacteristics(id = 3, nameRes = R.string.caption_barbecue, imageRes = R.drawable.barbecue_card),
            StoveCharacteristics(id = 4, nameRes = R.string.caption_fire_pit, imageRes = R.drawable.fire_pit_card)
        )
    val materials =
        listOf(
            StoveCharacteristics(id = 1, nameRes = R.string.caption_brick, imageRes = R.drawable.brick),
            StoveCharacteristics(id = 2, nameRes = R.string.caption_concrete, imageRes = R.drawable.concrete)
        )
}

data class StoveCharacteristics(
    val id: Int,
    val nameRes: Int,
    val imageRes: Int
)