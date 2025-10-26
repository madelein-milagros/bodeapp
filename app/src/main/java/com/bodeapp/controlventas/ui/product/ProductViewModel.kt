package com.bodeapp.controlventas.ui.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bodeapp.controlventas.data.AppDatabase
import com.bodeapp.controlventas.data.ProductoRepository
import com.bodeapp.controlventas.model.Producto
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductoRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = ProductoRepository(db.productoDao())
    }

    fun insertProducto(nombre: String, precio: Double, stock: Int) {
        viewModelScope.launch {
            repository.insert(Producto(nombre = nombre, precio = precio, stock = stock))
        }
    }
}