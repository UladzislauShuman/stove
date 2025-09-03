package com.example.stove.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primaryContainer = DarkPrimaryContainerColor,
    onPrimaryContainer = OnDarkPrimaryContainerColor,
    secondaryContainer = DarkSecondaryContainerColor,
    onSecondaryContainer = OnDarkSecondaryContainerColor,
    background = DarkBackgroundSurface,
    surface = DarkBackgroundSurface
)

private val LightColorScheme = lightColorScheme(
    primaryContainer = Amber,
    secondaryContainer = CloudGray,
    onPrimaryContainer = RavenBlack,
    onSecondaryContainer = SlateBlue,
    background = White,
    surface = White
)

@Composable
fun StoveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = shapes
    )
}