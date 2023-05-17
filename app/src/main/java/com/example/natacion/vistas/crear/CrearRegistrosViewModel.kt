package com.example.natacion.vistas.crear

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Registro
import com.example.natacion.network.NetworkRegistro
import com.example.natacion.repository.DataRepository
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class CrearRegistrosViewModel(dataSource: DataDao, application: Application) :
    AndroidViewModel(application) {

    private val registrosRepository = DataRepository(dataSource)

    val database = dataSource


    init {
        
    }

    fun insertRegistro(registro: RequestBody){
        viewModelScope.launch {
            registrosRepository.insertRetgistro(registro)
        }
    }

}