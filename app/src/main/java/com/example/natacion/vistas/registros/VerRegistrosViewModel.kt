package com.example.natacion.vistas.registros

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Usuario
import com.example.natacion.network.RegistroNetwork
import kotlinx.coroutines.launch

class VerRegistrosViewModel(dataSource: DataDao, application: Application) :
    AndroidViewModel(application) {

    val database = dataSource

    private val _usuarioLogged = MutableLiveData<Usuario?>()
    val usuarioLogged: LiveData<Usuario?> get() = _usuarioLogged

    private val _completeDelete = MutableLiveData<Boolean>()
    val completeDelete: LiveData<Boolean> get() = _completeDelete

    init {
        getDataUsuario()
    }

    fun getDataUsuario() {
        viewModelScope.launch {
            _usuarioLogged.value = getUsuarioDataBase()
        }
    }

    fun deleteRegistro(id: Int) {
        viewModelScope.launch {
            val registroNuevo = RegistroNetwork.registros.deleteRegistro(id)
            if (registroNuevo != null) {
                Log.d("AAAAAAAAAAAAAAA", registroNuevo.toString())
                _completeDelete.value = true
            }
        }
    }

    suspend fun getUsuarioDataBase(): Usuario? {
        return database.isLogged()
    }

}