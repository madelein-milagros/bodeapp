package com.bodeapp.controlventas.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

data class ProductoVendido(
    val nombre: String,
    @ColumnInfo(name = "total_vendido") val totalVendido: Int
)