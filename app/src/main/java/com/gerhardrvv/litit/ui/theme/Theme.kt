package com.gerhardrvv.litit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = LitItColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val LightColorPalette = LitItColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun LitItTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.background.copy(alpha = AlphaNearOpaque)
        )
    }

    ProvideLitItColors(colors) {
        MaterialTheme(
            colors = MaterialTheme.colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object LitItTheme {
    val colors: LitItColors
        @Composable
        get() = LocalLitItColors.current
}

@Stable
class LitItColors(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    background: Color,
    surface: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color
) {
    var primary by mutableStateOf(primary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var background by mutableStateOf(background)
        private set
    var surface by mutableStateOf(surface)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set

    fun update(other: LitItColors) {
        primary = other.primary
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        background = other.background
        surface = other.surface
        onPrimary = other.onPrimary
        onSecondary = other.onSecondary
        onBackground = other.onBackground
        onSurface = other.onSurface
    }

    fun copy(): LitItColors = LitItColors(
        primary = primary,
        primaryVariant = primaryVariant,
        secondary = secondary,
        background = background,
        surface = surface,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        onBackground = onBackground,
        onSurface = onSurface,
    )
}

@Composable
fun ProvideLitItColors(
    colors: LitItColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        colors.copy()
    }

    CompositionLocalProvider(LocalLitItColors provides colorPalette, content = content)
}

private val LocalLitItColors = staticCompositionLocalOf<LitItColors> {
    error("Color not found")
}
