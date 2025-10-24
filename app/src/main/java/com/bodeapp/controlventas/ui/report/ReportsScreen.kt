package com.bodeapp.controlventas.ui.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ReportsScreen(navController: NavController) {
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
                    imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
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

        // Hecho por Mayela
        // TODO: Agregar tarjetas de ventas, compras y utilidad

        Text(
            text = "jueves, 23 de octubre de 2025",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReportCard(title = "Total de Ventas", value = "S/ 0.00", subtitle = "0 transacciones", color = Color.Green)
        Spacer(modifier = Modifier.height(16.dp))
        ReportCard(title = "Total de Compras", value = "S/ 0.00", subtitle = "0 compras registradas", color = Color.Red)
        Spacer(modifier = Modifier.height(16.dp))
        ReportCard(title = "Utilidad Neta", value = "S/ 0.00", subtitle = "Ganancia del día", color = Color(0xFF667EEA), isHeader = true)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No hay transacciones registradas hoy",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ReportCard(title: String, value: String, subtitle: String, color: Color, isHeader: Boolean = false) {
    Card(
        modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium),
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

