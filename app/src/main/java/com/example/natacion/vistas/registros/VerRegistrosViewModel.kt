package com.example.natacion.vistas.registros

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Usuario
import kotlinx.coroutines.launch

class VerRegistrosViewModel(dataSource: DataDao, application: Application) :
    AndroidViewModel(application) {

    val database = dataSource

    private val _usuarioLogged = MutableLiveData<Usuario?>()
    val usuarioLogged: LiveData<Usuario?> get() = _usuarioLogged

    init {
        getDataUsuario()
    }

    fun getDataUsuario() {
        viewModelScope.launch {
            _usuarioLogged.value = getUsuarioDataBase()
        }
    }

    suspend fun getUsuarioDataBase(): Usuario? {
        return database.isLogged()
    }

}