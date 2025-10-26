package com.bodeapp.controlventas.ui.report

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bodeapp.controlventas.data.AppDatabase
import kotlinx.coroutines.flow.map
import java.time.LocalDate.now

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private val ventaDao = AppDatabase.getDatabase(application).ventaDao()
    private val compraDao = AppDatabase.getDatabase(application).compraDao()
    private val fechaHoy = now().toString()

    // Ahora retornan Flow<Double> en lugar de LiveData
    val totalVentas = ventaDao.getVentasByDate(fechaHoy)
        .map { ventas -> ventas.sumOf { it.total } }

    val totalCompras = compraDao.getComprasByDate(fechaHoy)
        .map { compras -> compras.sumOf { it.total } }
}