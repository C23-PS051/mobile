package com.dicoding.c23ps051.caferecommenderapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Brown,
    secondary = DarkBrown,
    onPrimary = White,
    onSecondary = White,
    secondaryContainer = Brown, // Selected navbar background color
    tertiary = Brown, // So text color can change
    outline = LightCream, // For borders etc
    outlineVariant = DarkGray, // For disabled borders
    surfaceVariant = DarkBrown, // Card background color
    onSecondaryContainer = LightCream, // Selected navbar icon color
    onSurfaceVariant = Brown, // Navbar icon color
    onTertiary = White, // Text color on card
    inverseSurface = LightCream,
    inverseOnSurface = Black,
)

private val LightColorScheme = lightColorScheme(
    primary = DarkBrown,
    secondary = Brown,
    onPrimary = White,
    onSecondary = White,
    secondaryContainer = LightCream, // Selected navbar background color
    tertiary = LightCream, // So text color can change
    outline = Gray, // For borders etc
    outlineVariant = LightGray, // For disabled borders
    surfaceVariant = LightCream, // Card background color
    onSecondaryContainer = DarkBrown, // Selected navbar icon color
    onSurfaceVariant = Brown, // Navbar icon color
    onTertiary = LightBlack, // Text color on card
    inverseSurface = Brown,
    inverseOnSurface = White,
)

@Composable
fun CafeRecommenderAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkBrown.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}