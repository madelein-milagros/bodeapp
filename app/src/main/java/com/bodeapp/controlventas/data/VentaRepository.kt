package com.bodeapp.controlventas.data

import com.bodeapp.controlventas.model.Venta

class VentaRepository(private val dao: VentaDao) {
    fun getVentasHoy(fecha: String) = dao.getVentasByDate(fecha)

    suspend fun insert(venta: Venta) = dao.insertVenta(venta)
}