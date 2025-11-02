package com.bodeapp.controlventas.ui.report

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.controlventas.model.Compra
import com.bodeapp.controlventas.model.ProductoVendido
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ReportsScreen(
    navController: NavController,
    viewModel: ReportViewModel = viewModel()
) {
    val totalVentas by viewModel.totalVentas.collectAsStateWithLifecycle(initialValue = 0.0)
    val totalCompras by viewModel.totalCompras.collectAsStateWithLifecycle(initialValue = 0.0)
    val comprasDetalle by viewModel.comprasDetalle.collectAsStateWithLifecycle(initialValue = emptyList())
    val productosMasVendidos by viewModel.productosMasVendidos.collectAsStateWithLifecycle(initialValue = emptyList())

    val utilidad = totalVentas - totalCompras
    val fechaHoy = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy", Locale("es")))

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
                    text = "Cierre de Caja",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Text(
            text = fechaHoy.replaceFirstChar { it.uppercase() },
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ReportCard(
                    title = "Total de Ventas",
                    value = "S/ ${String.format("%.2f", totalVentas)}",
                    subtitle = "${viewModel.getCountVentas()} transacciones",
                    color = Color(0xFF4CAF50)
                )
            }

            item {
                ReportCard(
                    title = "Total de Compras",
                    value = "S/ ${String.format("%.2f", totalCompras)}",
                    subtitle = "${viewModel.getCountCompras()} compras registradas",
                    color = Color(0xFFF44336)
                )
            }

            item {
                ReportCard(
                    title = "Utilidad Neta",
                    value = "S/ ${String.format("%.2f", utilidad)}",
                    subtitle = if (utilidad >= 0) "Ganancia del día" else "Pérdida del día",
                    color = if (utilidad >= 0) Color(0xFF2196F3) else Color(0xFFFF9800),
                    isHeader = true
                )
            }

            if (comprasDetalle.isNotEmpty()) {
                item {
                    Text(
                        text = "Detalle de Compras",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                items(comprasDetalle) { compra ->
                    CompraDetalleItem(compra = compra)
                }
            }

            if (productosMasVendidos.isNotEmpty()) {
                item {
                    Text(
                        text = "Productos más vendidos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                items(productosMasVendidos) { producto ->
                    ProductoVendidoItem(producto = producto)
                }
            }

            if (totalVentas == 0.0 && totalCompras == 0.0) {
                item {
                    Text(
                        text = "No hay transacciones registradas hoy",
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ReportCard(
    title: String,
    value: String,
    subtitle: String,
    color: Color,
    isHeader: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isHeader) color else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isHeader) Color.White else Color.Black
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isHeader) Color.White.copy(alpha = 0.8f) else Color.Gray
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = if (isHeader) Color.White else color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CompraDetalleItem(compra: Compra) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = compra.nombreProducto,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${compra.cantidad} x S/ ${String.format("%.2f", compra.costoUnitario)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Text(
                text = "S/ ${String.format("%.2f", compra.total)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProductoVendidoItem(producto: ProductoVendido) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${producto.totalVendido} uds",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
    }
}