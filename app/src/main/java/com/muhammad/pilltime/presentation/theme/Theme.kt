package com.muhammad.pilltime.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorScheme = lightColorScheme(
    primary = Primary60,
    primaryContainer = Primary50,
    onPrimary = Primary100,
    onPrimaryContainer = Color(0xFFEEF0FF),
    inversePrimary = Secondary80,

    secondary = Secondary30,
    secondaryContainer = Secondary50,
    onSecondary = Secondary95,

    background = NeutralVariant99,
    surface = Color.Gray,
    surfaceVariant = Color(0xFFE1E2EC),
    onSurface = NeutralVariant10,
    onSurfaceVariant = NeutralVariant30,

    outline = NeutralVariant50,
    outlineVariant = NeutralVariant80,

    error = LightError,
    errorContainer = LightErrorContainer,
    onError = LightOnError,
    onErrorContainer = LightOnErrorContainer
)

private val darkColorScheme = darkColorScheme(
    primary = Primary60,
    primaryContainer = Primary40,
    onPrimary = Primary100,
    onPrimaryContainer = Primary90,
    inversePrimary = Secondary70,

    secondary = Secondary70,
    secondaryContainer = Secondary50,
    onSecondary = Secondary95,

    background = NeutralVariant10,
    surface =  Color(0xFF5C5438),
    surfaceVariant = Color(0xFF2C2C2E),
    onSurface = NeutralVariant99,
    onSurfaceVariant = NeutralVariant80,

    outline = NeutralVariant50,
    outlineVariant = NeutralVariant80,
    error = DarkError,
    errorContainer = DarkErrorContainer,
    onError = Color.White,
    onErrorContainer = Color.White
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PillTimeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val context = LocalContext.current
    val view = LocalView.current
    val activity = (context as Activity)
    val window = activity.window
    SideEffect{
        WindowCompat.getInsetsController(window,view).isAppearanceLightStatusBars = !darkTheme
        WindowCompat.getInsetsController(window,view).isAppearanceLightStatusBars = !darkTheme
    }
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme
    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        motionScheme = MotionScheme.expressive()
    )
}