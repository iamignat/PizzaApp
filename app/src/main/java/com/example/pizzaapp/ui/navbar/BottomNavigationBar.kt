package com.example.pizzaapp.ui.navbar

import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pizzaapp.ui.theme.White
import com.example.pizzaapp.util.NavBarItems

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = White
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(colors = NavigationBarItemColors(
                selectedIconColor = Color.Transparent,
                selectedTextColor = Color.Transparent,
                selectedIndicatorColor = navItem.selectedColor,
                unselectedIconColor = Color.Transparent,
                unselectedTextColor = Color.Transparent,
                disabledIconColor = Color.Transparent,
                disabledTextColor = Color.Transparent,
            ), selected = currentRoute == navItem.route, onClick = {
                navController.navigate(navItem.route) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }, icon = {
                Image(
                    painter = painterResource(navItem.image),
                    contentDescription = navItem.title
                )
            }, label = {
                Text(
                    color= navItem.color, text = navItem.title, fontFamily = navItem.font,
                )
            })
        }
    }
}
