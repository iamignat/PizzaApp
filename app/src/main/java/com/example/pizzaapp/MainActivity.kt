package com.example.pizzaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pizzaapp.ui.navbar.BottomNavigationBar
import com.example.pizzaapp.ui.screens.DodoPizzaScreen
import com.example.pizzaapp.ui.screens.DominosPizzaScreen
import com.example.pizzaapp.ui.screens.PizzaFoxScreen
import com.example.pizzaapp.ui.theme.PizzaAppTheme
import com.example.pizzaapp.util.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzaAppTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    Column {
        NavHost(
            navController,
            startDestination = Routes.PizzaFoxScreen.route,
            modifier = Modifier.weight(1f)
        ) {
            composable(Routes.PizzaFoxScreen.route) {
                PizzaFoxScreen(
                    onNavigateAway = { navController.navigate(Routes.PizzaFoxScreen.route) }
                )
            }
            composable(Routes.DominosPizzaScreen.route) {
                DominosPizzaScreen(
                    onNavigateAway = { navController.navigate(Routes.DominosPizzaScreen.route) }
                )
            }
            composable(Routes.DodoPizzaScreen.route) {
                DodoPizzaScreen(
                    onNavigateAway = { navController.navigate(Routes.DodoPizzaScreen.route) }
                )
            }
        }
        BottomNavigationBar(navController = navController)
    }
}
