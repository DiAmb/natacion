package com.example.natacion.vistas.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONObject

class LoginViewModel(application: Application) :
    AndroidViewModel(application) {

    private lateinit var auth: FirebaseAuth

    private val _loginSucess = MutableLiveData<Boolean>()
    val loginSucess: LiveData<Boolean> get() = _loginSucess

    private val _loginFailed = MutableLiveData<Boolean>()
    val loginFailed: LiveData<Boolean> get() = _loginFailed

    init {
        auth = Firebase.auth
    }

    fun registrarUsuario(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                //HACER ALGO AL REGISTRAR CORRECTAMENTE
            }
    }

    fun accederUsuario(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    _loginSucess.value = true
                } else {
                    _loginFailed.value = true
                }
            }
    }


}