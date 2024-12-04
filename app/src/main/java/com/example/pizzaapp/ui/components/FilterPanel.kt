package com.example.pizzaapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzaapp.ui.theme.Black
import com.example.pizzaapp.ui.theme.DodoFontFamily
import com.example.pizzaapp.ui.theme.White

@Composable
fun FilterPanel(
    onFilterChange: (String, String) -> Unit,
    onResetFilters: () -> Unit,
    onClose: () -> Unit,
    color: Color,
    font: FontFamily
) {
    var includeText by remember { mutableStateOf("") }
    var excludeText by remember { mutableStateOf("") }

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
                        modifier = Modifier.size(28.dp),
                        tint = color
                    )
                }
            }

            OutlinedTextField(
                value = includeText,
                onValueChange = { includeText = it },
                label = { Text(text = "Включить ингредиенты", fontFamily = font, color = color, fontSize = 16.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true,
                textStyle = TextStyle(fontFamily = font, color = color, fontSize = 16.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = color,
                    focusedBorderColor = color,
                    unfocusedLabelColor = color,
                    focusedLabelColor = color,
                    cursorColor = color
                )
            )
            OutlinedTextField(
                value = excludeText,
                onValueChange = { excludeText = it },
                label = { Text(text = "Исключить ингредиенты", fontFamily = font, color = color, fontSize = 16.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true,
                textStyle = TextStyle(fontFamily = font, color = color, fontSize = 16.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = color,
                    focusedBorderColor = color,
                    unfocusedLabelColor = color,
                    focusedLabelColor = color,
                    cursorColor = color
                )
            )

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
                        onFilterChange(includeText, excludeText)
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
            onClose = onClose,
            color = Black,
            font = DodoFontFamily
        )
    }
}
