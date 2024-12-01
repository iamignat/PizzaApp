package com.example.pizzaapp.util.parser

import com.example.pizzaapp.util.ListItem

interface Parser {
    suspend fun getParsedData(): List<ListItem>
}
