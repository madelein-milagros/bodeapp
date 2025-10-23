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
        composable("home") { HomeScreen() }
        composable("product") { ProductRegistrationScreen() }
        composable("sale") { SalesScreen() }
        composable("purchase") { PurchasesScreen() }
        composable("report") { ReportsScreen() }
    }
}