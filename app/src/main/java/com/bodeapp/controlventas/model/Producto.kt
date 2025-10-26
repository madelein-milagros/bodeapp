package com.bodeapp.controlventas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val precio: Double,
    var stock: Int
)