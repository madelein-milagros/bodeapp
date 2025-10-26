package com.bodeapp.controlventas.ui.sale

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bodeapp.controlventas.data.AppDatabase
import com.bodeapp.controlventas.model.Venta
import kotlinx.coroutines.launch
import java.time.LocalDate

class SaleViewModel(application: Application) : AndroidViewModel(application) {

    private val ventaDao = AppDatabase.getDatabase(application).ventaDao()

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertVenta(productoId: Long, cantidad: Int, total: Double) {
        val fecha = LocalDate.now().toString()
        viewModelScope.launch {
            ventaDao.insertVenta(Venta(productoId = productoId, cantidad = cantidad, total = total, fecha = fecha))
        }
    }
}