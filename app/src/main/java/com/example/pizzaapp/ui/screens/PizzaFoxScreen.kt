package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

    LaunchedEffect(Unit) {
        val parser: Parser = FoxParser()
        withContext(Dispatchers.IO) {
            foxList = parser.getParsedData()
        }
    }

    Scaffold(
        topBar = { TopBar("Пицца лисица") }
    ) { innerPadding ->
        LazyColumn(
            modifier =
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(foxList) { item ->
                FoxListItem(item = item)
            }
        }
    }
}
