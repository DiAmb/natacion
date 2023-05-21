package com.example.natacion.vistas.admin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Usuario
import com.example.natacion.database.UsuarioDelete
import com.example.natacion.database.UsuarioUpdate
import com.example.natacion.network.RegistroNetwork
import com.example.natacion.repository.DataRepository
import kotlinx.coroutines.launch

class AdminViewModel(val dataSource: DataDao, application: Application) :
    AndroidViewModel(application) {

    private val registrosRepository = DataRepository(dataSource)

    val database = dataSource

    private val _usuarios = MutableLiveData<List<Usuario>?>()
    val usuarios: LiveData<List<Usuario>?> get() = _usuarios

    private val _usuarioLogged = MutableLiveData<Usuario?>()
    val usuarioLogged: LiveData<Usuario?> get() = _usuarioLogged

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        _loading.value = true
        getDataUsuario()
    }

    fun changeState() {
        _loading.value = !_loading.value!!
    }


    fun getUsuarios(usuario: Usuario) {
        viewModelScope.launch {
            getUsuariosNetwork(usuario)
            changeState()
        }
    }

    private suspend fun getUsuariosNetwork(usuario: Usuario) {
        val result = RegistroNetwork.registros.getUsuarios(usuario).body()
        if (result != null) {
            _usuarios.value = result
        }
    }

    fun updateUsuario(usuarioUpdate: Usuario) {
        changeState()
        viewModelScope.launch {
            updateUsuariosNetwork(usuarioUpdate)
            Thread.sleep(500)
            changeState()
        }
    }

    private suspend fun updateUsuariosNetwork(usuarioUpdate: Usuario) {
        var usuarioAdmin = usuarioLogged.value
        var data = UsuarioUpdate(usuarioAdmin, usuarioUpdate)
        val result = RegistroNetwork.registros.updateUsuario(data).body()
        if (result != null) {
            _usuarios.value = result
        }
    }


    fun deleteUsuario(correo: String) {
        changeState()
        viewModelScope.launch {
            deleteUsuariosNetwork(correo)
            Thread.sleep(500)
            changeState()
        }
    }

    private suspend fun deleteUsuariosNetwork(correo: String) {
        var usuarioAdmin = usuarioLogged.value
        var data = UsuarioDelete(usuarioAdmin, correo)
        val result = RegistroNetwork.registros.deleteUsuario(data).body()
        if (result != null) {
            _usuarios.value = result
        }
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