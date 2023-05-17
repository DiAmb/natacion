package com.example.natacion.vistas.loading

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Usuario
import kotlinx.coroutines.launch

class LoadingViewModel(dataSource:DataDao,application: Application) :
    AndroidViewModel(application) {

    private val database = dataSource

    init {
        
    }

    suspend fun hasData(): Usuario? {
        return database.isLogged()
    }

}