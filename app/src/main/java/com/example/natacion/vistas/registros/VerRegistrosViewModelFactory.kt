package com.example.natacion.vistas.registros

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VerRegistrosViewModelFactory (
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerRegistrosViewModel::class.java)) {
            return VerRegistrosViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}