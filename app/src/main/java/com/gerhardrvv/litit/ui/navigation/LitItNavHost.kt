package com.gerhardrvv.litit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gerhardrvv.litit.ui.home.HomeScreen
import com.gerhardrvv.litit.ui.navigation.LitItScreens.Home
import com.gerhardrvv.litit.viewmodel.MainViewModel

@Composable
fun LitItNavHost(
    mainViewModel: MainViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Home.name,
    ) {
        composable(route = Home.name) {
            HomeScreen(mainViewModel)
        }
    }
}
