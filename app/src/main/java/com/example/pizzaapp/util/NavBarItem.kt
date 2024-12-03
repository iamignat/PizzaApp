package com.example.pizzaapp.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

data class BarItem(
    val title: String,
    val image: Int,
    val route: String,
    val font: FontFamily,
    val color: Color,
    val selectedColor: Color,
    val fontWeight: FontWeight
)