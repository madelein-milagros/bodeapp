package com.bodeapp.controlventas.ui.report

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bodeapp.controlventas.data.AppDatabase
import kotlinx.coroutines.flow.map

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private val ventaDao = AppDatabase.getDatabase(application).ventaDao()
    private val compraDao = AppDatabase.getDatabase(application).compraDao()
    private val fechaHoy = java.time.LocalDate.now().toString()

    // ✅ Ahora usamos Flow directamente (sin .asLiveData)
    val totalVentas = ventaDao.getVentasByDate(fechaHoy)
        .map { ventas -> ventas.sumOf { it.total } }

    val totalCompras = compraDao.getComprasByDate(fechaHoy)
        .map { compras -> compras.sumOf { it.total } }

    val productosMasVendidos = ventaDao.getProductosMasVendidos(fechaHoy)
}