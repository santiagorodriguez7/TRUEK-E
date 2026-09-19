package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = NavyDark,
    onPrimary = SurfaceWhite,
    primaryContainer = Slate100,
    onPrimaryContainer = NavyDark,
    secondary = EmeraldPrimary,
    onSecondary = SurfaceWhite,
    secondaryContainer = EmeraldSurface,
    onSecondaryContainer = EmeraldDark,
    tertiary = GoldAccent,
    onTertiary = SurfaceWhite,
    tertiaryContainer = GoldSurface,
    onTertiaryContainer = GoldAccent,
    background = SurfaceLight,
    onBackground = NavyDark,
    surface = SurfaceWhite,
    onSurface = NavyDark,
    surfaceVariant = Slate50,
    onSurfaceVariant = Slate700,
    outline = Slate200,
    outlineVariant = Slate300
)

private val DarkColorScheme = darkColorScheme(
    primary = SurfaceWhite,
    onPrimary = NavyDark,
    primaryContainer = NavyMedium,
    onPrimaryContainer = SurfaceWhite,
    secondary = EmeraldLight,
    onSecondary = NavyDark,
    secondaryContainer = EmeraldDark,
    onSecondaryContainer = EmeraldSurface,
    tertiary = GoldAccent,
    onTertiary = NavyDark,
    background = NavyDark,
    onBackground = SurfaceWhite,
    surface = NavyMedium,
    onSurface = SurfaceWhite,
    surfaceVariant = Slate800,
    onSurfaceVariant = Slate300,
    outline = Slate700,
    outlineVariant = Slate600
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep Truek-E brand identity consistent
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
