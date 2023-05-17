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

    init {
        
    }

    fun updateRegistro(registro: Registro) {
        viewModelScope.launch {
            val registroNuevo = RegistroNetwork.registros.updateRegistro(registro).body()
            _backHome.value = registroNuevo != null
        }
    }

}