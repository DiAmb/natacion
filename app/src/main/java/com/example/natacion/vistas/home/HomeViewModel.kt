package com.example.natacion.vistas.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Registro
import com.example.natacion.repository.DataRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException

class HomeViewModel(dataSource: DataDao, application: Application) :
    AndroidViewModel(application) {

    private val registrosRepository = DataRepository(dataSource)

    val database = dataSource

    private val _registros = MutableLiveData<List<Registro>?>()
    val registros: LiveData<List<Registro>?> get() = _registros

    init {
        getRegistros()
    }

    fun getRegistros(){
        viewModelScope.launch {
            registrosRepository.refreshRegistros()
            _registros.value = getRegistrosFromDatabase()
        }
    }

    private suspend fun getRegistrosFromDatabase():List<Registro>{
        return database.getRegistros()
    }



}