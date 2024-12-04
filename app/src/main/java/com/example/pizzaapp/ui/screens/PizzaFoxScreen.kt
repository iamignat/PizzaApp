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
import com.example.pizzaapp.ui.listItems.FoxListItem
import com.example.pizzaapp.ui.theme.BackgroundColor
import com.example.pizzaapp.ui.theme.MalinaFontFamily
import com.example.pizzaapp.ui.theme.Orange
import com.example.pizzaapp.ui.theme.White
import com.example.pizzaapp.util.ListItem
import com.example.pizzaapp.util.parser.FoxParser
import com.example.pizzaapp.util.parser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaFoxScreen(onNavigateAway: () -> Unit) {
    var foxList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }
    var filteredList by rememberSaveable { mutableStateOf<List<ListItem>>(emptyList()) }
    var showFilterPanel by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val parser: Parser = FoxParser()
        withContext(Dispatchers.IO) {
            foxList = parser.getParsedData()
            filteredList = foxList
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
                    title = {
                        Text(text = "Пицца лисица",
                            color = Orange,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MalinaFontFamily
                        )
                    },
                    colorScheme = TopAppBarColors(
                        containerColor = White,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = Orange,
                        actionIconContentColor = Orange,
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
                    FoxListItem(item = item)
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
                    onFilterChange = { include, exclude ->
                        filteredList = foxList.filter { item ->
                            val itemIngredients = item.ingredients.split(",").map { it.trim().lowercase() }.toSet()
                            val includeSet = if (include.isNotEmpty()) include.split(",").map { it.trim().lowercase() }.toSet() else emptySet()
                            val excludeSet = if (exclude.isNotEmpty()) exclude.split(",").map { it.trim().lowercase() }.toSet() else emptySet()
                            val includeMatch = includeSet.isEmpty() || includeSet.all { keyword -> itemIngredients.any { it.contains(keyword) } }
                            val excludeMatch = excludeSet.isEmpty() || excludeSet.none { keyword -> itemIngredients.any { it.contains(keyword) } }
                            includeMatch && excludeMatch
                        }
                    },
                    onResetFilters = {
                        filteredList = foxList
                    },
                    onClose = { showFilterPanel = false },
                    color = Orange,
                    font = MalinaFontFamily
                )
            }
        }
    }
}