// ui/sale/SaleViewModel.kt
package com.bodeapp.controlventas.ui.sale

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bodeapp.controlventas.data.AppDatabase
import com.bodeapp.controlventas.model.Venta
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class SaleViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val ventaDao = db.ventaDao()
    private val productoDao = db.productoDao()

    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent = _errorEvent.asSharedFlow()

    fun venderProducto(productoId: Long, cantidad: Int) {
        viewModelScope.launch {
            val producto = productoDao.getProductoById(productoId)
            if (producto == null) {
                _errorEvent.emit("Producto no encontrado")
                return@launch
            }
            if (producto.stock < cantidad) {
                _errorEvent.emit("Stock insuficiente")
                return@launch
            }

            // Actualizar stock
            val nuevoStock = producto.stock - cantidad
            productoDao.updateProducto(producto.copy(stock = nuevoStock))

            // Registrar venta
            val total = producto.precio * cantidad
            val fecha = LocalDate.now().toString()
            ventaDao.insertVenta(Venta(productoId = productoId, cantidad = cantidad, total = total, fecha = fecha))
        }
    }
}