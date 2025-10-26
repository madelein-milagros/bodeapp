package com.bodeapp.controlventas.ui.purchase

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bodeapp.controlventas.data.AppDatabase
import com.bodeapp.controlventas.data.CompraRepository
import com.bodeapp.controlventas.model.Compra
import kotlinx.coroutines.launch
import java.time.LocalDate

class PurchaseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CompraRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = CompraRepository(db.compraDao())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertCompra(nombre: String, costo: Double, cantidad: Int) {
        val total = costo * cantidad
        val fecha = LocalDate.now().toString()
        viewModelScope.launch {
            repository.insert(Compra(nombreProducto = nombre, costoUnitario = costo, cantidad = cantidad, total = total, fecha = fecha))
        }
    }
}