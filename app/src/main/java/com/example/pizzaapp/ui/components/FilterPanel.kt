package com.example.pizzaapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterPanel(
    onFilterChange: (String, String) -> Unit,
    onResetFilters: () -> Unit,
    onClose: () -> Unit
) {
    var includeText by remember { mutableStateOf("") }
    var excludeText by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 30.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Filter",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            OutlinedTextField(
                value = includeText,
                onValueChange = { includeText = it },
                label = { Text("Включить ингридиенты") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            OutlinedTextField(
                value = excludeText,
                onValueChange = { excludeText = it },
                label = { Text("Исключить ингридиенты") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton (
                    onClick = onResetFilters,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Очистить", modifier = Modifier.padding(6.dp))
                }
                OutlinedButton(
                    onClick = {
                        onFilterChange(includeText, excludeText)
                        onClose()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Принять", modifier = Modifier.padding(6.dp))
                }
            }
        }
    }
}

@Composable
fun FilterPanelAnimated(
    isVisible: Boolean,
    onFilterChange: (String, String) -> Unit,
    onResetFilters: () -> Unit,
    onClose: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut()
    ) {
        FilterPanel(
            onFilterChange = onFilterChange,
            onResetFilters = onResetFilters,
            onClose = onClose
        )
    }
}
