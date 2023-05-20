package com.example.natacion.vistas.loading

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Usuario
import com.example.natacion.network.RegistroNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingViewModel(dataSource: DataDao, application: Application) :
    AndroidViewModel(application) {

    private val database = dataSource

    private val _goTitle = MutableLiveData<Boolean>()
    val goTitle: LiveData<Boolean> get() = _goTitle

    private val _goLogin = MutableLiveData<Boolean>()
    val goLogin: LiveData<Boolean> get() = _goLogin


    init {
        _goTitle.value = false
        _goLogin.value = false
        checkLogin()
    }

    private fun checkLogin() {
        viewModelScope.launch {
            val currentUser = hasData()
            if (currentUser != null) {
                val resCurrentUsuario = RegistroNetwork.registros.loginUsuario(
                    Usuario(
                        currentUser.correo,
                        currentUser.password
                    )
                ).body()
                if (resCurrentUsuario != null) {
                    if (resCurrentUsuario.tipo != -1) {
                        insertarUsuarioDatabase(resCurrentUsuario)
                        delay(1000)
                        _goTitle.value = true
                    } else {
                        clearUsuarios()
                        delay(1000)
                        _goLogin.value = true
                    }
                } else {
                    clearUsuarios()
                    delay(1000)
                    _goLogin.value = true
                }
            } else {
                clearUsuarios()
                delay(1000)
                _goLogin.value = true
            }
        }
    }

    suspend fun hasData(): Usuario? {
        return database.isLogged()
    }

    suspend fun insertarUsuarioDatabase(usuario: Usuario) {
        database.inserUsuario(usuario)
    }

    suspend fun clearUsuarios() {
        database.deleteAllUsuario()
    }


}