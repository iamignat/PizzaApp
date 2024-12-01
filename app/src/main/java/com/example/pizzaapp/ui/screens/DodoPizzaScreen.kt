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
import com.example.pizzaapp.ui.listItems.DodoListItem
import com.example.pizzaapp.util.ListItem
import com.example.pizzaapp.util.parser.DodoParser
import com.example.pizzaapp.util.parser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DodoPizzaScreen() {
    var dodoList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        val parser: Parser = DodoParser()
        withContext(Dispatchers.IO) {
            dodoList = parser.getParsedData()
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
            items(dodoList) { item ->
                DodoListItem(item = item)
            }
        }
    }
}
