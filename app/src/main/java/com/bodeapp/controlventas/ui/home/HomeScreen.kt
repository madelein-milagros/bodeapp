package com.bodeapp.controlventas.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bodeapp.controlventas.R

@Composable
fun HomeScreen(navController: NavController) {
    val gradientBrush = Brush.verticalGradient(
        listOf(Color(0xFF667EEA), Color(0xFF764BA2))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8FF))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(gradientBrush)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Mi Bodega",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Sistema de ventas e inventario",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                HomeCard(
                    modifier = Modifier.weight(1f),
                    title = "Productos",
                    subtitle = "Registrar nuevos productos",
                    imageRes = R.drawable.ic_productos,
                    circleColor = Color(0xFF5A6FF0),
                    onClick = { navController.navigate("product") }
                )
                HomeCard(
                    modifier = Modifier.weight(1f),
                    title = "Ventas",
                    subtitle = "Realizar ventas",
                    imageRes = R.drawable.ic_ventas,
                    circleColor = Color(0xFF67D18B),
                    onClick = { navController.navigate("sale") }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                HomeCard(
                    modifier = Modifier.weight(1f),
                    title = "Compras",
                    subtitle = "Registrar compras",
                    imageRes = R.drawable.ic_compras,
                    circleColor = Color(0xFFA068F5),
                    onClick = { navController.navigate("purchase") }
                )
                HomeCard(
                    modifier = Modifier.weight(1f),
                    title = "Informes",
                    subtitle = "Cierre de caja",
                    imageRes = R.drawable.ic_informes,
                    circleColor = Color(0xFFF2579D),
                    onClick = { navController.navigate("report") }
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Gestiona tu negocio de manera simple",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Composable
fun HomeCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    imageRes: Int,
    circleColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(160.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(circleColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                textAlign = TextAlign.Center
            )
        }
    }
}
