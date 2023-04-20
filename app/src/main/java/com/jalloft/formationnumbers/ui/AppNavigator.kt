package com.jalloft.formationnumbers.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jalloft.formationnumbers.ui.screens.HomeScreen
import com.jalloft.formationnumbers.ui.screens.PresentationScreen


@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppDestination.PresentationDestination.route
    ) {
        composable(AppDestination.PresentationDestination.route) {
            PresentationScreen(
                startClick = {
                    navController.navigate(AppDestination.HomeDestination.route)
                }
            )
        }
        composable(AppDestination.HomeDestination.route) {
            HomeScreen()
        }
    }
}

sealed class AppDestination(val route: String) {
    object PresentationDestination : AppDestination("presentation")
    object HomeDestination : AppDestination("home")
}