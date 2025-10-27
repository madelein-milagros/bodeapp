package com.bodeapp.controlventas.data

import androidx.room.*
import com.bodeapp.controlventas.model.ProductoVendido
import com.bodeapp.controlventas.model.Venta
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas WHERE fecha = :fecha")
    fun getVentasByDate(fecha: String): Flow<List<Venta>>

    @Insert
    suspend fun insertVenta(venta: Venta)

    @Query("""
    SELECT p.nombre, SUM(v.cantidad) as total_vendido
    FROM ventas v
    JOIN productos p ON v.productoId = p.id
    WHERE v.fecha = :fecha
    GROUP BY p.id, p.nombre
    ORDER BY total_vendido DESC
    LIMIT 5
""")
    fun getProductosMasVendidos(fecha: String): Flow<List<ProductoVendido>>
}