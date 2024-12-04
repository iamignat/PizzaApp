package com.example.pizzaapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.pizzaapp.ui.components.FilterPanel
import com.example.pizzaapp.ui.components.TopBar
import com.example.pizzaapp.ui.listItems.DominosListItem
import com.example.pizzaapp.ui.theme.BackgroundColor
import com.example.pizzaapp.util.ListItem
import com.example.pizzaapp.util.parser.DominosParser
import com.example.pizzaapp.util.parser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DominosPizzaScreen(onNavigateAway: () -> Unit) {
    var dominosList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }
    var filteredList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }
    var showFilterPanel by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val parser: Parser = DominosParser()
        withContext(Dispatchers.IO) {
            dominosList = parser.getParsedData()
            filteredList = dominosList
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                showFilterPanel = false
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    BackHandler(enabled = showFilterPanel) {
        showFilterPanel = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = BackgroundColor,
            topBar = {
                TopBar(
                    title = "Пицца Доминос",
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
                    .background(color = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f))
                    .clickable { showFilterPanel = false }
            )
            Box(modifier = Modifier.fillMaxSize()) {
                FilterPanel(
                    onFilterChange = { include, exclude ->
                        filteredList = dominosList.filter { item ->
                            val itemIngredients = item.ingredients.split(",").map { it.trim().lowercase() }.toSet()
                            val includeSet = if (include.isNotEmpty()) include.split(",").map { it.trim().lowercase() }.toSet() else emptySet()
                            val excludeSet = if (exclude.isNotEmpty()) exclude.split(",").map { it.trim().lowercase() }.toSet() else emptySet()
                            val includeMatch = includeSet.isEmpty() || includeSet.all { keyword -> itemIngredients.any { it.contains(keyword) } }
                            val excludeMatch = excludeSet.isEmpty() || excludeSet.none { keyword -> itemIngredients.any { it.contains(keyword) } }
                            includeMatch && excludeMatch
                        }
                    },
                    onResetFilters = {
                        filteredList = dominosList
                    },
                    onClose = { showFilterPanel = false }
                )
            }
        }
    }
}