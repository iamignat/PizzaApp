package com.example.pizzaapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onFilterChange: (String) -> Unit) {
    var textState = rememberSaveable { mutableStateOf("") }

    TopAppBar(
        title = { Text(text = title) },
        actions = {
            OutlinedTextField(
                value = textState.value,
                onValueChange = {
                    textState.value = it
                    onFilterChange(it)
                },
                placeholder = { Text("Фильтр...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}
