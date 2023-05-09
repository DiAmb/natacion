package com.example.natacion.vistas.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.natacion.modelos.Registro

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel(application: Application) :
    AndroidViewModel(application) {

    lateinit var spaceRef: StorageReference
    var storageRef: StorageReference
    private val _registros = MutableLiveData<List<Registro>?>()
    val registros: LiveData<List<Registro>?> get() = _registros

    private lateinit var auth: FirebaseAuth


    init {
        auth = Firebase.auth
        val storage = Firebase.storage("gs://natacion-8706f.appspot.com/")
        storageRef = storage.reference
        getRegistros()

    }
    fun getRegistros(){
        viewModelScope.launch {
            getRegistrosFirebase()
        }
    }
    suspend fun getRegistrosFirebase(){
        val db = Firebase.firestore

        val user = auth.currentUser


            val registrosRef = db.collection("registros")
            registrosRef.get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val registro = document.toObject<Registro>()
                    Log.d("TAG", "Registro: $registro")
                }
            }.addOnFailureListener { exception ->
                Log.d("TAG", "Error al obtener registros: ", exception)
            }




    }

}