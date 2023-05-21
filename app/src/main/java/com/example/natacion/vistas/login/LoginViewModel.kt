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

    private val _loginSucess = MutableLiveData<Int>()
    val loginSucess: LiveData<Int> get() = _loginSucess

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val database = dataSource

    init {
        _loading.value = false
        _loginSucess.value = -100
    }

    fun changeState() {
        _loading.value = !_loading.value!!
    }

    fun accederUsuario(email: String, password: String) {
        changeState()
        viewModelScope.launch {
            val usuarioResponse =
                RegistroNetwork.registros.loginUsuario(Usuario(email, password)).body()
            if (usuarioResponse != null) {
                //-1 SIN AUTORIZACION
                //0 CON AUTORIZACION DE LECTURA
                //1 CON AUTORIZACION DE EDITOR
                //2 ADMINISTRADOR
                when (usuarioResponse.tipo) {
                    -1 -> {
                        _loginSucess.value = 0
                    }
                    0 -> {
                        _loginSucess.value = 1
                        database.inserUsuario(usuarioResponse)
                    }
                    1 -> {
                        _loginSucess.value = 1
                        database.inserUsuario(usuarioResponse)
                    }
                    2 -> {
                        _loginSucess.value = 1
                        database.inserUsuario(usuarioResponse)
                    }
                }
            } else {
                _loginSucess.value = -1
            }
            changeState()
        }
    }

    fun resetCodeLogin() {
        _loginSucess.value = -100
    }


}