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

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        _loading.value = false
        getDataUsuario()
    }

    fun changeState() {
        _loading.value = !_loading.value!!
    }

    fun getDataUsuario() {
        changeState()
        viewModelScope.launch {
            _usuarioLogged.value = getUsuarioDataBase()
            changeState()
        }
    }

    fun deleteRegistro(id: Int) {
        changeState()
        viewModelScope.launch {
            val registroNuevo = RegistroNetwork.registros.deleteRegistro(id)
            if (registroNuevo != null) {
                Log.d("AAAAAAAAAAAAAAA", registroNuevo.toString())
                changeState()
                _completeDelete.value = true
            }
        }
    }

    suspend fun getUsuarioDataBase(): Usuario? {
        return database.isLogged()
    }

}