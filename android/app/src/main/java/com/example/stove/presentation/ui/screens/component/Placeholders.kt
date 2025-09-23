package com.example.stove.presentation.ui.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer

@Composable
fun TextPlaceholder(text: String, style: TextStyle) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(text),
        style = style,
    )
    val placeholderWidth = with(density) { textLayoutResult.size.width.toDp() }
    val placeholderHeight = with(density) { textLayoutResult.size.height.toDp() }

    Box(
        modifier = Modifier
            .width(placeholderWidth)
            .height(placeholderHeight)
            .background(MaterialTheme.colorScheme.surfaceContainer)
    )
}