package com.example.natacion.vistas.home

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Favoritos
import com.example.natacion.database.Registro
import com.example.natacion.database.Usuario
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

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _dataChange = MutableLiveData<Boolean>()
    val dataChange: LiveData<Boolean> get() = _dataChange

    val database = dataSource

    private val _registros = MutableLiveData<List<Registro>?>()
    val registros: LiveData<List<Registro>?> get() = _registros

    private val _logOutSuccess = MutableLiveData<Boolean>()
    val logOutSuccess: LiveData<Boolean> get() = _logOutSuccess

    private val _usuarioLogged = MutableLiveData<Usuario?>()
    val usuarioLogged: LiveData<Usuario?> get() = _usuarioLogged


    init {
        _loading.value = false
        _dataChange.value = false
        getRegistros()
        getDataUsuario()
        _logOutSuccess.value = false
    }

    fun changeState() {
        _loading.value = !_loading.value!!
    }

    fun getRegistros() {
        changeState()
        viewModelScope.launch {
            registrosRepository.refreshRegistros()
            _registros.value = getRegistrosFromDatabase()
            changeState()
        }
    }

    fun getAllRegistros() {
        changeState()
        viewModelScope.launch {
            _registros.value = getRegistrosFromDatabase()
            changeState()
        }
    }

    fun getRegistrosByNumero(numero: Int) {
        changeState()
        viewModelScope.launch {
            _registros.value = getRegistrosFromDatabaseByNumero(numero)
            changeState()
        }
    }

    fun getRegistrosByTitulo(titulo: String) {
        changeState()
        viewModelScope.launch {
            _registros.value = getRegistrosFromDatabaseByTitulo(titulo)
            changeState()
        }
    }

    fun getRegistrosByFavoritos() {
        changeState()
        viewModelScope.launch {
            var favoritos = getFavoritosID()
            var ids = ArrayList<Int>()
            for (fav in favoritos) {
                fav.id?.let { ids.add(it) }
            }
            _registros.value = getRegistrosFromDatabaseByFavoritos(ids)
            changeState()
        }
    }


    private suspend fun getRegistrosFromDatabase(): List<Registro> {
        return database.getRegistros()
    }

    private suspend fun getFavoritosID(): List<Favoritos> {
        return database.getFavoritos()
    }

    private suspend fun getRegistrosFromDatabaseByFavoritos(ids: ArrayList<Int>): List<Registro> {
        return database.getRegistrosFavoritos(ids)
    }

    private suspend fun getRegistrosFromDatabaseByTitulo(titulo: String): List<Registro> {
        return database.getRegistrosByTitulo(titulo)
    }

    private suspend fun getRegistrosFromDatabaseByNumero(numero: Int): List<Registro> {
        return database.getRegistrosByNumero(numero)
    }

    fun logOut() {
        viewModelScope.launch {
            clearLoginDatabase()
            _logOutSuccess.value = true
        }
    }

    suspend private fun clearLoginDatabase() {
        database.deleteAllUsuario()
    }

    fun getDataUsuario() {
        viewModelScope.launch {
            _usuarioLogged.value = getUsuarioDataBase()
        }
    }

    suspend fun getUsuarioDataBase(): Usuario? {
        return database.isLogged()
    }

    fun disableChange(){
        _dataChange.value = false
    }

    //FAVORITOS

    fun insertFavoritos(id: Int, context: Context, titulo: String) {
        viewModelScope.launch {
            if (getFavoritosDatabase(id) == null) {
                insertFavoritosDatabase(id)
                Toast.makeText(
                    context,
                    titulo + ": Agregado a la lista de favoritos.",
                    Toast.LENGTH_SHORT
                ).show()
                _dataChange.value = true
            } else {
                deleteFavoritosDatabase(id)
                Toast.makeText(
                    context,
                    titulo + ": Eliminado de la lista de favoritos.",
                    Toast.LENGTH_SHORT
                ).show()
                _dataChange.value = true
            }
        }
    }

    private suspend fun insertFavoritosDatabase(id: Int) {
        database.addFavorito(Favoritos(id))
    }

    private suspend fun deleteFavoritosDatabase(id: Int) {
        database.deleteFavorito(id)
    }

    private suspend fun getFavoritosDatabase(id: Int): Favoritos {
        return database.getFavorito(id)
    }


}