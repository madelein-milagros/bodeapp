package com.bodeapp.controlventas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compras")
data class Compra(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombreProducto: String,
    val costoUnitario: Double,
    val cantidad: Int,
    val total: Double,
    val fecha: String // Formato: "2025-10-26"
)