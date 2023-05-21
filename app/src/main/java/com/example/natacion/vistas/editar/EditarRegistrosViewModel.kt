package com.example.natacion.vistas.editar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.Registro
import com.example.natacion.network.RegistroNetwork
import kotlinx.coroutines.launch

class EditarRegistrosViewModel(application: Application) :
    AndroidViewModel(application) {

    private val _backHome = MutableLiveData<Boolean>(false)
    val backHome: LiveData<Boolean> get() = _backHome

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        _loading.value = false
    }

    fun changeState() {
        _loading.value = !_loading.value!!
    }

    fun updateRegistro(registro: Registro) {
        changeState()
        viewModelScope.launch {
            val registroNuevo = RegistroNetwork.registros.updateRegistro(registro).body()
            _backHome.value = registroNuevo != null
            changeState()
        }
    }

}