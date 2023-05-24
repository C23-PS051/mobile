package com.dicoding.c23ps051.caferecommenderapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFF896B60),
    primaryVariant = Color(0xFFD0C7C2),
    secondary = Color(0xFF4A332D),
    onPrimary = Color(0xFFFFFFFF)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF4A332D),
    primaryVariant = Color(0xFFD0C7C2),
    secondary = Color(0xFF896B60),
    onPrimary = Color(0xFFFFFFFF)

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CafeRecommenderAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}