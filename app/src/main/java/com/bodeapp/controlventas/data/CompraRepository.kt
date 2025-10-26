package com.bodeapp.controlventas.data

import com.bodeapp.controlventas.model.Compra

class CompraRepository(private val dao: CompraDao) {
    fun getComprasHoy(fecha: String) = dao.getComprasByDate(fecha)

    suspend fun insert(compra: Compra) = dao.insertCompra(compra)
}