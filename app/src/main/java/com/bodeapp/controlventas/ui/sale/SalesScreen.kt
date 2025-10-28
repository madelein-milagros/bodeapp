// ui/sale/SalesScreen.kt
package com.bodeapp.controlventas.ui.sale

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SalesScreen(
    navController: NavController,
    viewModel: SaleViewModel = viewModel()
) {
    var productoId by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // 1. Remember a coroutine scope
    val scope = rememberCoroutineScope()

    // Escuchar errores del ViewModel
    LaunchedEffect(viewModel) {
        viewModel.errorEvent.collectLatest { error ->
            showError = error
            if (error.isNotEmpty()) {
                snackbarHostState.showSnackbar(error)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5F1))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.Gray
                )
            }
            Text(
                text = "Ventas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Text("Realizar nueva venta", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))

        // Show success message
        if (showSuccess) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(
                    text = "✓ Venta registrada exitosamente",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        OutlinedTextField(
            value = productoId,
            onValueChange = { productoId = it },
            label = { Text("ID del Producto") },
            placeholder = { Text("Ej: 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            placeholder = { Text("Ej: 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val id = productoId.toLongOrNull()
                val cant = cantidad.toIntOrNull()
                if (id == null || cant == null || cant <= 0) {
                    // 2. Launch the coroutine to show the snackbar
                    scope.launch {
                        snackbarHostState.showSnackbar("Ingresa un ID y cantidad válidos")
                    }
                    return@Button
                }

                viewModel.venderProducto(id, cant)
                showSuccess = true
                productoId = ""
                cantidad = ""

                // Hide success message after 2 seconds
                scope.launch { // It's also good practice to use the remembered scope here
                    kotlinx.coroutines.delay(2000)
                    showSuccess = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF667EEA))
        ) {
            Text("Registrar Venta", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }

    // Snackbar for errors
    SnackbarHost(hostState = snackbarHostState)
}

