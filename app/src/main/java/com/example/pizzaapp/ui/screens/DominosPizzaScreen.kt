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
import com.example.pizzaapp.ui.listItems.DominosListItem
import com.example.pizzaapp.util.ListItem
import com.example.pizzaapp.util.parser.DominosParser
import com.example.pizzaapp.util.parser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DominosPizzaScreen() {
    var dominosList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }
    var filteredList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        val parser: Parser = DominosParser()
        withContext(Dispatchers.IO) {
            dominosList = parser.getParsedData()
            filteredList = dominosList
        }
    }

    Scaffold(
        topBar = {
            TopBar("Пицца Доминос") { filter ->
                filteredList = if (filter.isEmpty()) {
                    dominosList
                } else {
                    dominosList.filter { it.ingredients.contains(filter, ignoreCase = true) }
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
                DominosListItem(item = item)
            }
        }
    }
}
