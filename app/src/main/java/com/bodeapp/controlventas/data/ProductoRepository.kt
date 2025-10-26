package com.bodeapp.controlventas.data

import com.bodeapp.controlventas.model.Producto

class ProductoRepository(private val dao: ProductoDao) {
    val allProductos = dao.getAllProductos()

    suspend fun insert(producto: Producto) = dao.insertProducto(producto)
    suspend fun update(producto: Producto) = dao.updateProducto(producto)
}