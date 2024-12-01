package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.pizzaapp.ui.components.TopBar
import com.example.pizzaapp.ui.listItems.FoxListItem
import com.example.pizzaapp.util.ListItem
import com.example.pizzaapp.util.parser.FoxParser
import com.example.pizzaapp.util.parser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PizzaFoxScreen() {
    var foxList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }
    var filteredList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        val parser: Parser = FoxParser()
        withContext(Dispatchers.IO) {
            foxList = parser.getParsedData()
            filteredList = foxList
        }
    }

    Scaffold(
        topBar = {
            TopBar("Пицца Лисица") { filter ->
                filteredList = if (filter.isEmpty()) {
                    foxList
                } else {
                    foxList.filter { it.ingredients.contains(filter, ignoreCase = true) }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(filteredList) { item ->
                FoxListItem(item = item)
            }
        }
    }
}
