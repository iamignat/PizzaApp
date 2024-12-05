package com.example.pizzaapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.pizzaapp.ui.components.FilterPanel
import com.example.pizzaapp.ui.components.TopBar
import com.example.pizzaapp.ui.listItems.DominosListItem
import com.example.pizzaapp.ui.theme.BackgroundColor
import com.example.pizzaapp.ui.theme.Black
import com.example.pizzaapp.ui.theme.GothamProFontFamily
import com.example.pizzaapp.ui.theme.White
import com.example.pizzaapp.util.ListItem
import com.example.pizzaapp.util.parser.DominosParser
import com.example.pizzaapp.util.parser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DominosPizzaScreen(onNavigateAway: () -> Unit) {
    var dominosList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }
    var filteredList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }
    var showFilterPanel by rememberSaveable { mutableStateOf(false) }
    val excludeListIngredients = listOf("")

    LaunchedEffect(Unit) {
        val parser: Parser = DominosParser()
        withContext(Dispatchers.IO) {
            dominosList = parser.getParsedData()
            filteredList = dominosList
        }
    }

    val ingredients = dominosList.flatMap { it.ingredients.split(Regex("[,/]|\\s+и\\s+")).map { it.trim().lowercase() } }
        .distinct()
        .filter { it !in excludeListIngredients }


    BackHandler(enabled = showFilterPanel) {
        showFilterPanel = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = BackgroundColor,
            topBar = {
                TopBar(
                    title = {
                        Text(text = "Domino's",
                            color = Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = GothamProFontFamily
                        )
                    },
                    colorScheme = TopAppBarColors(
                        containerColor = White,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = Black,
                        actionIconContentColor = Black,
                        navigationIconContentColor = Color.Transparent,
                    ),
                    onOpenFilterPanel = { showFilterPanel = !showFilterPanel }
                )
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

        if (showFilterPanel) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.5f))
                    .clickable { showFilterPanel = false }
            )
            Box(modifier = Modifier.fillMaxSize()) {
                FilterPanel(
                    ingredients = ingredients,
                    onFilterChange = { include, exclude ->
                        filteredList = dominosList.filter { item ->
                            val itemIngredients = item.ingredients.split(Regex("[,/]|\\s+и\\s+")).map { it.trim().lowercase() }.toSet()

                            val includeMatch = include.isEmpty() || include.all { it.lowercase() in itemIngredients }
                            val excludeMatch = exclude.isEmpty() || exclude.none { it.lowercase() in itemIngredients }

                            includeMatch && excludeMatch
                        }
                    },
                    onResetFilters = {
                        filteredList = dominosList
                    },
                    onClose = { showFilterPanel = false },
                    color = Black,
                    font = GothamProFontFamily
                )
            }
        }
    }
}