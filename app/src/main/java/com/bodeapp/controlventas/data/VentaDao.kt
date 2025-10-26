package com.bodeapp.controlventas.data

import androidx.room.*
import com.bodeapp.controlventas.model.Venta
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas WHERE fecha = :fecha")
    fun getVentasByDate(fecha: String): Flow<List<Venta>>

    @Insert
    suspend fun insertVenta(venta: Venta)
}