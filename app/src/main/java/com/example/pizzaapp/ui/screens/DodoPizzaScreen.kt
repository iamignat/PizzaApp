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
import com.example.pizzaapp.ui.listItems.DodoListItem
import com.example.pizzaapp.ui.theme.BackgroundColor
import com.example.pizzaapp.ui.theme.Black
import com.example.pizzaapp.ui.theme.DodoFontFamily
import com.example.pizzaapp.ui.theme.White
import com.example.pizzaapp.util.ListItem
import com.example.pizzaapp.util.parser.DodoParser
import com.example.pizzaapp.util.parser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
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
                    title = {
                        Text(
                            text = "Додо Пицца",
                            color = Black,
                            fontWeight = FontWeight.W600,
                            fontFamily = DodoFontFamily
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
                    DodoListItem(item = item)
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
                        filteredList = dodoList.filter { item ->
                            val itemIngredients =
                                item.ingredients.split(",").map { it.trim().lowercase() }.toSet()
                            val includeSet = if (include.isNotEmpty()) include.split(",")
                                .map { it.trim().lowercase() }.toSet() else emptySet()
                            val excludeSet = if (exclude.isNotEmpty()) exclude.split(",")
                                .map { it.trim().lowercase() }.toSet() else emptySet()
                            val includeMatch = includeSet.isEmpty() || includeSet.all { keyword ->
                                itemIngredients.any {
                                    it.contains(keyword)
                                }
                            }
                            val excludeMatch = excludeSet.isEmpty() || excludeSet.none { keyword ->
                                itemIngredients.any {
                                    it.contains(keyword)
                                }
                            }
                            includeMatch && excludeMatch
                        }
                    },
                    onResetFilters = {
                        filteredList = dodoList
                    },
                    onClose = { showFilterPanel = false },
                    color = Black,
                    font = DodoFontFamily
                )
            }
        }
    }
}