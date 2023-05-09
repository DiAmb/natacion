package com.example.natacion.vistas.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.natacion.modelos.Registro

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.ktx.Firebase

class HomeViewModel(application: Application) :
    AndroidViewModel(application) {

    private val _registros = MutableLiveData<List<Registro>?>()
    val registros: LiveData<List<Registro>?> get() = _registros

    private lateinit var auth: FirebaseAuth


    init {
        auth = Firebase.auth

    }

}