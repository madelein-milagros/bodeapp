package com.bodeapp.controlventas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bodeapp.controlventas.ui.home.HomeScreen
import com.bodeapp.controlventas.ui.product.ProductRegistrationScreen
import com.bodeapp.controlventas.ui.purchase.PurchasesScreen
import com.bodeapp.controlventas.ui.report.ReportsScreen
import com.bodeapp.controlventas.ui.sale.SalesScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("product") {
            ProductRegistrationScreen(navController = navController)
        }
        composable("sale") {
            SalesScreen(navController = navController)
        }
        composable("purchase") {
            PurchasesScreen(navController = navController)
        }
        composable("report") {
            ReportsScreen(navController = navController)
        }
    }
}