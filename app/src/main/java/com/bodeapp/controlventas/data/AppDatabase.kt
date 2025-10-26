package com.bodeapp.controlventas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bodeapp.controlventas.model.Compra
import com.bodeapp.controlventas.model.Producto
import com.bodeapp.controlventas.model.Venta

@Database(
    entities = [Producto::class, Venta::class, Compra::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun ventaDao(): VentaDao
    abstract fun compraDao(): CompraDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bodeapp_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}