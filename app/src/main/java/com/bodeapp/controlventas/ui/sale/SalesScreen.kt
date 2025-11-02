package com.bodeapp.controlventas.ui.sale

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.controlventas.model.Producto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(
    navController: NavController,
    viewModel: SaleViewModel = viewModel()
) {
    val productos by viewModel.productos.collectAsStateWithLifecycle(initialValue = emptyList())
    val carrito by viewModel.carrito.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    var cantidades by remember { mutableStateOf(emptyMap<Long, Int>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8FF))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF667EEA), Color(0xFF764BA2))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Ventas",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Text(
            text = "Realizar nueva venta",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Productos Disponibles",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productos) { producto ->
                        ProductoCard(
                            producto = producto,
                            cantidad = cantidades[producto.id] ?: 0,
                            onIncrement = {
                                val nuevaCantidad = (cantidades[producto.id] ?: 0) + 1
                                if (nuevaCantidad <= producto.stock) {
                                    cantidades = cantidades + (producto.id to nuevaCantidad)
                                }
                            },
                            onDecrement = {
                                val nuevaCantidad = (cantidades[producto.id] ?: 0) - 1
                                if (nuevaCantidad >= 0) {
                                    cantidades = cantidades + (producto.id to nuevaCantidad)
                                }
                            }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Carrito de Compras",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val itemsEnCarrito = productos.filter { (cantidades[it.id] ?: 0) > 0 }

                    items(itemsEnCarrito) { producto ->
                        CarritoItem(
                            producto = producto,
                            cantidad = cantidades[producto.id] ?: 0,
                            onEliminar = {
                                cantidades = cantidades - producto.id
                            }
                        )
                    }
                }

                val total = productos.sumOf { producto ->
                    val cantidad = cantidades[producto.id] ?: 0
                    producto.precio * cantidad
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal:")
                            Text("S/ ${String.format("%.2f", total)}")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total:", fontWeight = FontWeight.Bold)
                            Text("S/ ${String.format("%.2f", total)}", fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {

                                val ventasRealizadas = mutableListOf<Boolean>()
                                productos.forEach { producto ->
                                    val cantidad = cantidades[producto.id] ?: 0
                                    if (cantidad > 0) {
                                        viewModel.venderProducto(producto.id, cantidad)
                                        ventasRealizadas.add(true)
                                    }
                                }

                                if (ventasRealizadas.isNotEmpty()) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("¡Venta completada exitosamente!")

                                        cantidades = emptyMap()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = total > 0,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Text("Completar Venta - S/ ${String.format("%.2f", total)}")
                        }
                    }
                }
            }
        }
    }


    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun ProductoCard(
    producto: Producto,
    cantidad: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "S/ ${String.format("%.2f", producto.precio)} • Stock: ${producto.stock}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = cantidad.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    IconButton(
                        onClick = onDecrement,
                        enabled = cantidad > 0
                    ) {
                        Text("-", style = MaterialTheme.typography.titleLarge)
                    }
                    IconButton(
                        onClick = onIncrement,
                        enabled = cantidad < producto.stock
                    ) {
                        Text("+", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
    }
}

@Composable
fun CarritoItem(
    producto: Producto,
    cantidad: Int,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$cantidad x S/ ${String.format("%.2f", producto.precio)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "S/ ${String.format("%.2f", producto.precio * cantidad)}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                FilledIconButton(
                    onClick = onEliminar,
                    modifier = Modifier.size(32.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("✓", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}