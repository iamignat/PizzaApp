package com.example.pizzaapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pizzaapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val MalinaFontFamily = FontFamily(
    Font(R.font.malina, FontWeight.Normal)
)
val HelveticaFontFamily = FontFamily(
    Font(R.font.helvetica, FontWeight.Normal)
)
val DodoFontFamily = FontFamily(
    Font(R.font.dodo, FontWeight.Normal)
)
val GothamProFontFamily = FontFamily(
    Font(R.font.gothampro, FontWeight.Normal),
    Font(R.font.gothamprobold, FontWeight.Bold)
)