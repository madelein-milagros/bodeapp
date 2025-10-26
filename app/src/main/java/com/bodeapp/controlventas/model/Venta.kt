package com.bodeapp.controlventas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ventas")
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productoId: Long,
    val cantidad: Int,
    val total: Double,
    val fecha: String // Formato: "2025-10-26"
)