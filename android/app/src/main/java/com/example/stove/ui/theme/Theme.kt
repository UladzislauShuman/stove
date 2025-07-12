package com.example.stove.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primaryContainer = Amber,
    secondaryContainer = CloudGray,
    onPrimaryContainer = RavenBlack,
    onSecondaryContainer = SlateBlue,
    background = White
)

private val LightColorScheme = lightColorScheme(
    primaryContainer = Amber,
    secondaryContainer = CloudGray,
    onPrimaryContainer = RavenBlack,
    onSecondaryContainer = SlateBlue,
    background = White
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