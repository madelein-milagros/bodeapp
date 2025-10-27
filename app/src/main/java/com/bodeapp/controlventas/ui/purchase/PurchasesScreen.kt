package com.bodeapp.controlventas.ui.purchase

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun PurchasesScreen(
    navController: NavController,
    viewModel: PurchaseViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var costo by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0FF))
            .padding(16.dp)
    ) {
        // Barra superior con botón de retroceso
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
                text = "Compras",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = "Registrar compra de insumos",
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Producto") },
            placeholder = { Text("Ej: Azúcar Rubia") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = costo,
            onValueChange = { costo = it },
            label = { Text("Costo Unitario (S/)") },
            placeholder = { Text("S/ 0.00") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            placeholder = { Text("0") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val c = costo.toDoubleOrNull()
                val q = cantidad.toIntOrNull()
                if (nombre.isNotBlank() && c != null && q != null && q > 0) {
                    viewModel.insertCompra(nombre, c, q)
                    showSuccess = true
                    nombre = ""
                    costo = ""
                    cantidad = ""
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE94E77))
        ) {
            Text("Registrar Compra", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }

    // Alertas
    if (showError) {
        SnackbarHost(hostState = SnackbarHostState()) {
            Snackbar(
                action = {
                    Button(onClick = { showError = false }, colors = ButtonDefaults.textButtonColors()) {
                        Text("Cerrar")
                    }
                }
            ) {
                Text("Completa todos los campos correctamente")
            }
        }
    }

    if (showSuccess) {
        SnackbarHost(hostState = SnackbarHostState()) {
            Snackbar(
                action = {
                    Button(onClick = { showSuccess = false }, colors = ButtonDefaults.textButtonColors()) {
                        Text("Cerrar")
                    }
                }
            ) {
                Text("¡Compra registrada!")
            }
        }
    }
}