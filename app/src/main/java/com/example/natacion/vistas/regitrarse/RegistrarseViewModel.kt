package com.example.natacion.vistas.regitrarse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Usuario
import com.example.natacion.network.RegistroNetwork
import kotlinx.coroutines.launch

class RegistrarseViewModel(dataSource: DataDao, application: Application) :
    AndroidViewModel(application) {

    private val _sendSuccess = MutableLiveData<Boolean>()
    val sendSuccess: LiveData<Boolean> get() = _sendSuccess

    private val _sendFailed = MutableLiveData<Boolean>()
    val sendFailed: LiveData<Boolean> get() = _sendFailed

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    init {
        _loading.value = false
        _sendFailed.value = false
        _sendSuccess.value = false
    }

    fun changeState() {
        _loading.value = !_loading.value!!
    }

    fun registrarUsuario(email: String, password: String, nombres: String, apellidos: String) {
        changeState()
        viewModelScope.launch {
            val usuario =
                RegistroNetwork.registros.registrarUsuario(
                    Usuario(
                        email,
                        password,
                        -1,
                        nombres,
                        apellidos
                    )
                ).body()
            if (usuario != null) {
                _sendSuccess.value = true
            } else {
                _sendFailed.value = true
            }
            changeState()
        }
    }
}