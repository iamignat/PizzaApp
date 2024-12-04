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
import com.example.pizzaapp.ui.listItems.DodoListItem
import com.example.pizzaapp.ui.theme.BackgroundColor
import com.example.pizzaapp.util.ListItem
import com.example.pizzaapp.util.parser.DodoParser
import com.example.pizzaapp.util.parser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DodoPizzaScreen(onNavigateAway: () -> Unit) {
    var dodoList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }
    var filteredList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }
    var showFilterPanel by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val parser: Parser = DodoParser()
        withContext(Dispatchers.IO) {
            dodoList = parser.getParsedData()
            filteredList = dodoList
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
                    title = "Пицца Додо",
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
                    DodoListItem(item = item)
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
                        filteredList = dodoList.filter { item ->
                            val itemIngredients = item.ingredients.split(",").map { it.trim().lowercase() }.toSet()
                            val includeSet = if (include.isNotEmpty()) include.split(",").map { it.trim().lowercase() }.toSet() else emptySet()
                            val excludeSet = if (exclude.isNotEmpty()) exclude.split(",").map { it.trim().lowercase() }.toSet() else emptySet()
                            (includeSet.isEmpty() || itemIngredients.containsAll(includeSet)) &&
                                    (excludeSet.isEmpty() || excludeSet.none { it in itemIngredients })
                        }
                    },
                    onResetFilters = {
                        filteredList = dodoList
                    },
                    onClose = { showFilterPanel = false }
                )
            }
        }
    }
}