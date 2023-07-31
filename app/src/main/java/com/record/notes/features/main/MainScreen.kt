package com.record.notes.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.record.notes.features.record.DataRecordViewScreen
import com.record.notes.features.home.HomeViewScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainViewScreen(navController: NavHostController) {
    val buttonNavController = rememberNavController()
    val pages = listOf(ButtonNavigationBar.Home, ButtonNavigationBar.Record)
    Scaffold (
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by buttonNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                pages.forEach { screen ->
                    BottomNavigationItem(modifier = Modifier.background(color = Color.White),
                        icon = {
                            Icon(
                                painterResource(screen.icon),
                                contentDescription = null,
                                // Set the desired icon color
                                tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                    Color.Black
                                } else {
                                    Color.Gray
                                }
                            )
                        },
                        label = {
                            Text(
                                screen.route,
                                style = TextStyle(fontSize = 10.sp),
                                color = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                    Color.Black
                                } else {
                                    Color.Gray
                                }
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            buttonNavController.navigate(screen.route) {
                                // on the back stack as users select items
                                popUpTo(buttonNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // selecting the same item
                                launchSingleTop = true
                                // Restore state when selecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            buttonNavController,
            startDestination = ButtonNavigationBar.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(ButtonNavigationBar.Home.route) { HomeViewScreen(buttonNavController, navController) }
            composable(ButtonNavigationBar.Record.route) { DataRecordViewScreen(buttonNavController) }
        }
    }
}
