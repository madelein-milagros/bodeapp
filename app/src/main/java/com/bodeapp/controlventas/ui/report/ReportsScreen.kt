package com.bodeapp.controlventas.ui.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ReportsScreen(
    navController: NavController,
    viewModel: ReportViewModel = viewModel()
) {
    val totalVentas by viewModel.totalVentas.collectAsStateWithLifecycle(initialValue = 0.0)
    val totalCompras by viewModel.totalCompras.collectAsStateWithLifecycle(initialValue = 0.0)
    val productosMasVendidos by viewModel.productosMasVendidos.collectAsStateWithLifecycle(initialValue = emptyList())

    val utilidad = totalVentas - totalCompras
    val fechaHoy = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5F5))
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
                text = "Cierre de Caja",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = fechaHoy.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReportCard(
            title = "Total de Ventas",
            value = "S/ ${String.format("%.2f", totalVentas)}",
            subtitle = "ventas registradas",
            color = Color(0xFF4CAF50)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReportCard(
            title = "Total de Compras",
            value = "S/ ${String.format("%.2f", totalCompras)}",
            subtitle = "compras registradas",
            color = Color(0xFFF44336)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReportCard(
            title = "Utilidad Neta",
            value = "S/ ${String.format("%.2f", utilidad)}",
            subtitle = "Ganancia del día",
            color = Color(0xFF667EEA),
            isHeader = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ Productos más vendidos
        if (productosMasVendidos.isNotEmpty()) {
            Text(
                text = "Productos más vendidos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            productosMasVendidos.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item.nombre)
                        Text("${item.totalVendido} uds")
                    }
                }
            }
        } else if (totalVentas == 0.0 && totalCompras == 0.0) {
            Text(
                text = "No hay transacciones registradas hoy",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
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
            .clip(MaterialTheme.shapes.medium),
        colors = CardDefaults.cardColors(
            containerColor = if (isHeader) color else Color.White
        )
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