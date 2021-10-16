package com.bnp.tiktaktoe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bnp.tiktaktoe.R


private val LightColorPalette = lightColors(
    primary = Color(R.color.board_background),
    primaryVariant = Color(R.color.board_background),
    secondary = Teal200
)

@Composable
fun TTTTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors =
        LightColorPalette


    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}