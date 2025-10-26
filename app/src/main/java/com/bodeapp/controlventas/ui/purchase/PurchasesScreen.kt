package com.bodeapp.controlventas.ui.purchase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PurchasesScreen(
    navController: NavController,
    viewModel: PurchaseViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    Button(onClick = {
        if (name.isNotBlank() && cost.isNotBlank() && quantity.isNotBlank()) {
            val c = cost.toDoubleOrNull() ?: 0.0
            val q = quantity.toIntOrNull() ?: 0
            viewModel.insertCompra(name, c, q)
            navController.popBackStack()
        }
    }) {
        Text("Registrar Compra")
    }
}
