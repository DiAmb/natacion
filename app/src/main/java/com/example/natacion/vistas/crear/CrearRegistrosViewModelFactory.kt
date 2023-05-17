package com.example.natacion.vistas.crear

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.natacion.database.DataDao

class CrearRegistrosViewModelFactory (
    private val dataSource: DataDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CrearRegistrosViewModel::class.java)) {
            return CrearRegistrosViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}