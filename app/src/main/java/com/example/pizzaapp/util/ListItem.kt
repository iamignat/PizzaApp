package com.example.pizzaapp.util

data class ListItem(
    val title: String,
    val imageName: String,
    val ingredients : List<String>,
    val price : Map<String, Double>,
)