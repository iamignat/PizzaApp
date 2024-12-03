package com.example.pizzaapp.util

import androidx.compose.ui.text.font.FontWeight
import com.example.pizzaapp.R
import com.example.pizzaapp.ui.theme.Black
import com.example.pizzaapp.ui.theme.DodoFontFamily
import com.example.pizzaapp.ui.theme.FoxSelected
import com.example.pizzaapp.ui.theme.GothamProFontFamily
import com.example.pizzaapp.ui.theme.MalinaFontFamily
import com.example.pizzaapp.ui.theme.Orange
import com.example.pizzaapp.ui.theme.OtherSelected

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Пицца лисица",
            image = R.drawable.fox,
            route = "pizza_fox",
            font = MalinaFontFamily,
            color = Orange,
            selectedColor = FoxSelected,
            fontWeight = FontWeight.Bold,
        ),
        BarItem(
            title = "Domino's",
            image = R.drawable.dominos,
            route = "pizza_dominos",
            font = GothamProFontFamily,
            color = Black,
            selectedColor = OtherSelected,
            fontWeight = FontWeight.Bold,
        ),
        BarItem(
            title = "Додо Пицца",
            image = R.drawable.dodo,
            route = "pizza_dodo",
            font = DodoFontFamily,
            color = Black,
            selectedColor = OtherSelected,
            fontWeight = FontWeight.W600
        ),
    )
}