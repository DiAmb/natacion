package com.example.natacion.vistas.crear

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Registro
import com.example.natacion.network.RegistroNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "BACKEND ACTION"

class CrearRegistrosViewModel(dataSource: DataDao, application: Application) :
    AndroidViewModel(application) {

    private val _backHome = MutableLiveData<Boolean>(false)
    val backHome: LiveData<Boolean> get() = _backHome

    init {
    }

    fun insertRegistro(registro: Registro) {
        viewModelScope.launch {
            val registroNuevo = RegistroNetwork.registros.insertRegistro(registro).body()
            _backHome.value = registroNuevo != null
        }
    }

}