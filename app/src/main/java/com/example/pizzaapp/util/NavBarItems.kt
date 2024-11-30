package com.example.pizzaapp.util

import com.example.pizzaapp.R

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Пицца лисица",
            image = R.drawable.fox,
            route = "pizza_fox",
        ),
        BarItem(
            title = "Domino's",
            image = R.drawable.dominos,
            route = "pizza_dominos",
        ),
        BarItem(
            title = "ДОДО пицца",
            image = R.drawable.dodo,
            route = "pizza_dodo",
        ),
    )
}