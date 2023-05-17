package com.example.natacion.vistas.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.database.DataDao
import com.example.natacion.database.Usuario
import com.example.natacion.network.RegistroNetwork
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class LoginViewModel(dataSource: DataDao, application: Application) :
    AndroidViewModel(application) {

    private val _loginSucess = MutableLiveData<Boolean>()
    val loginSucess: LiveData<Boolean> get() = _loginSucess

    private val _loginFailed = MutableLiveData<Boolean>()
    val loginFailed: LiveData<Boolean> get() = _loginFailed


    private val database = dataSource

    init {
        _loginFailed.value = false
        _loginSucess.value = false
    }

    fun registrarUsuario(email: String, password: String) {
        viewModelScope.launch {
            val usuarioResponse =
                RegistroNetwork.registros.registrarUsuario(Usuario(email, password)).body()
            if (usuarioResponse != null) {
                val usuario = Usuario(
                    usuarioResponse.usuario,
                    usuarioResponse.password,
                    usuarioResponse.tipo == 1
                )
                database.deleteAllUsuario()
                database.inserUsuario(usuario)
                _loginSucess.value = true
            } else {
                _loginFailed.value = true
            }


        }
    }

    fun accederUsuario(email: String, password: String) {
        viewModelScope.launch {
            val usuarioResponse =
                RegistroNetwork.registros.loginUsuario(Usuario(email, password)).body()

            if (usuarioResponse != null) {
                val usuario = Usuario(
                    usuarioResponse.usuario,
                    usuarioResponse.password,
                    usuarioResponse.tipo == 1
                )
                database.deleteAllUsuario()
                database.inserUsuario(usuario)
                _loginSucess.value = true
            } else {
                _loginFailed.value = true
            }
        }
    }


}