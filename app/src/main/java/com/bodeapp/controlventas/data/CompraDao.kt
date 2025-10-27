package com.bodeapp.controlventas.data

import androidx.room.*
import com.bodeapp.controlventas.model.Compra
import kotlinx.coroutines.flow.Flow

@Dao
interface CompraDao {
    @Query("SELECT * FROM compras WHERE fecha = :fecha")
    fun getComprasByDate(fecha: String): Flow<List<Compra>>

    @Insert
    suspend fun insertCompra(compra: Compra)
}