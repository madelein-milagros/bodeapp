package com.bodeapp.controlventas.data

import androidx.room.*
import com.bodeapp.controlventas.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos")
    fun getAllProductos(): Flow<List<Producto>>

    @Insert
    suspend fun insertProducto(producto: Producto)

    @Update
    suspend fun updateProducto(producto: Producto)

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getProductoById(id: Long): Producto?
}