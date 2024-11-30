package com.example.pizzaapp.util

sealed class Routes(val route: String) {
    data object PizzaFoxScreen : Routes("pizza_fox")
    data object DominosPizzaScreen : Routes("pizza_dominos")
    data object DodoPizzaScreen : Routes("pizza_dodo")
}