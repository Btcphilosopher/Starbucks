package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val StarbucksLightColorScheme = lightColorScheme(
    primary = StarbucksHouseGreen,
    onPrimary = Color.White,
    primaryContainer = StarbucksLightGreen,
    onPrimaryContainer = StarbucksDarkGreen,
    secondary = StarbucksGold,
    onSecondary = Color.White,
    secondaryContainer = StarbucksCardBg,
    onSecondaryContainer = StarbucksCoffeeBrown,
    tertiary = StarbucksCoffeeBrown,
    onTertiary = Color.White,
    background = StarbucksWarmCream,
    onBackground = StarbucksDarkText,
    surface = StarbucksSurface,
    onSurface = StarbucksDarkText,
    surfaceVariant = StarbucksCardBg,
    onSurfaceVariant = StarbucksSubtext
)

private val StarbucksDarkColorScheme = darkColorScheme(
    primary = StarbucksHouseGreen,
    onPrimary = Color.White,
    primaryContainer = StarbucksDarkGreen,
    onPrimaryContainer = StarbucksLightGreen,
    secondary = StarbucksGold,
    onSecondary = Color.White,
    background = Color(0xFF121815),
    onBackground = Color(0xFFE2E8E5),
    surface = Color(0xFF1A221E),
    onSurface = Color(0xFFE2E8E5),
    surfaceVariant = Color(0xFF24302A),
    onSurfaceVariant = Color(0xFFA0AEA6)
)

@Composable
fun StarbucksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) StarbucksDarkColorScheme else StarbucksLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

