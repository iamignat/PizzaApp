package com.example.pizzaapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzaapp.ui.theme.AcceptCheckboxColor
import com.example.pizzaapp.ui.theme.DeclineCheckboxColor
import com.example.pizzaapp.ui.theme.White


@Composable
fun FilterPanel(
    ingredients: List<String>,
    onFilterChange: (List<String>, List<String>) -> Unit,
    onResetFilters: () -> Unit,
    onClose: () -> Unit,
    color: Color,
    font: FontFamily
) {
    var includeIngredients by remember { mutableStateOf<List<String>>(emptyList()) }
    var excludeIngredients by remember { mutableStateOf<List<String>>(emptyList()) }

    // Scrollable Column
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(scrollState)  // Enable scrolling
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Filter",
                        modifier = Modifier.size(30.dp),
                        tint = color
                    )
                }
            }

            // Include Ingredients Checkbox
            Text(text = "Включить ингредиенты", fontFamily = font, color = color, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            ingredients.forEach { ingredient ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = includeIngredients.contains(ingredient),
                        onCheckedChange = { isChecked ->
                            includeIngredients = if (isChecked) {
                                includeIngredients + ingredient
                            } else {
                                includeIngredients - ingredient
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = AcceptCheckboxColor,
                            checkmarkColor = White,
                            uncheckedColor = color
                        )
                    )
                    Text(text = ingredient, fontFamily = font, color = color, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Exclude Ingredients Checkbox
            Text(text = "Исключить ингредиенты", fontFamily = font, color = color, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            ingredients.forEach { ingredient ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = excludeIngredients.contains(ingredient),
                        onCheckedChange = { isChecked ->
                            excludeIngredients = if (isChecked) {
                                excludeIngredients + ingredient
                            } else {
                                excludeIngredients - ingredient
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DeclineCheckboxColor,
                            checkmarkColor = White,
                            uncheckedColor = color
                        )
                    )
                    Text(text = ingredient, fontFamily = font, color = color, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onResetFilters,
                    modifier = Modifier.weight(1f),
                    colors = ButtonColors(
                        containerColor = White,
                        contentColor = color,
                        disabledContentColor = color,
                        disabledContainerColor = White
                    ),
                    border = BorderStroke(2.dp, color)
                ) {
                    Text(
                        "Очистить",
                        modifier = Modifier.padding(6.dp),
                        fontFamily = font,
                        color = color,
                        fontSize = 18.sp
                    )
                }
                OutlinedButton(
                    onClick = {
                        onFilterChange(includeIngredients, excludeIngredients)
                        onClose()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonColors(
                        containerColor = White,
                        contentColor = color,
                        disabledContentColor = color,
                        disabledContainerColor = White
                    ),
                    border = BorderStroke(2.dp, color)
                ) {
                    Text(
                        "Принять",
                        modifier = Modifier.padding(6.dp),
                        fontFamily = font,
                        color = color,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}