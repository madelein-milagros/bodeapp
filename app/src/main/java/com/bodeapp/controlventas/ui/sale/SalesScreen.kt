// ui/sale/SalesScreen.kt
package com.bodeapp.controlventas.ui.sale

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SalesScreen(
    navController: NavController,
    viewModel: SaleViewModel = viewModel()
) {
    var productoId by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf("") }

    // Escuchar errores del ViewModel
    LaunchedEffect(viewModel) {
        viewModel.errorEvent.collectLatest { error ->
            showError = error
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
                if (id != null && cant != null && cant > 0) {
                    viewModel.venderProducto(id, cant)
                    productoId = ""
                    cantidad = ""
                } else {
                    showError = "Ingresa un ID y cantidad válidos"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF667EEA))
        ) {
            Text("Registrar Venta", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }

    // Mostrar error si existe
    if (showError.isNotEmpty()) {
        SnackbarHost(hostState = SnackbarHostState()) {
            Snackbar(
                action = {
                    Button(onClick = { showError = "" }, colors = ButtonDefaults.textButtonColors()) {
                        Text("Cerrar")
                    }
                }
            ) {
                Text(showError)
            }
        }
    }
}