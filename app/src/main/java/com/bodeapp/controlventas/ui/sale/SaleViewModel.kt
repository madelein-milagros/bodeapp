package com.bodeapp.controlventas.ui.sale

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bodeapp.controlventas.data.AppDatabase
import com.bodeapp.controlventas.model.Producto
import com.bodeapp.controlventas.model.Venta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate

class SaleViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val ventaDao = db.ventaDao()
    private val productoDao = db.productoDao()

    val productos = productoDao.getAllProductos()


    private val _carrito = MutableStateFlow<Map<Long, Int>>(emptyMap())
    val carrito: StateFlow<Map<Long, Int>> = _carrito.asStateFlow()

    fun venderProducto(productoId: Long, cantidad: Int) {
        viewModelScope.launch {
            val producto = productoDao.getProductoById(productoId)
            if (producto == null) {

                return@launch
            }
            if (producto.stock < cantidad) {

                return@launch
            }


            val nuevoStock = producto.stock - cantidad
            productoDao.updateProducto(producto.copy(stock = nuevoStock))


            val total = producto.precio * cantidad
            val fecha = LocalDate.now().toString()
            ventaDao.insertVenta(Venta(productoId = productoId, cantidad = cantidad, total = total, fecha = fecha))
        }
    }

    fun agregarAlCarrito(productoId: Long, cantidad: Int) {
        val nuevoCarrito = _carrito.value.toMutableMap()
        nuevoCarrito[productoId] = cantidad
        _carrito.value = nuevoCarrito
    }

    fun limpiarCarrito() {
        _carrito.value = emptyMap()
    }
}