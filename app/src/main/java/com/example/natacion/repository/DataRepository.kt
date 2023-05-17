package com.example.natacion.repository

import android.util.Log
import com.example.natacion.database.DataDao
import com.example.natacion.database.Registro
import com.example.natacion.network.NetworkRegistro
import com.example.natacion.network.RegistroNetwork
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository(private val database: DataDao) {

    suspend fun refreshRegistros() {
        withContext(Dispatchers.IO) {
            val registros = RegistroNetwork.registros.getRegistros()
            database.insertAll(registros.map {
                Registro(
                    numero = it.numero,
                    titulo = it.titulo,
                    subtitulo = it.subtitulo,
                    descripcion = it.descripcion,
                    imagen = it.imagen,
                    audio = it.audio,
                )
            })
        }
    }


    fun insertRetgistro(registro: RequestBody) {


        MainScope().launch {
            val response = RegistroNetwork.registros.insertRegistro(registro)
            if (response.isSuccessful) {

                // Convert raw JSON to pretty JSON using GSON library
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(
                    JsonParser.parseString(
                        response.body()
                            ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                    )
                )

                Log.d("Pretty Printed JSON :", prettyJson)

            } else {

                Log.e("RETROFIT_ERROR", response.code().toString())

            }
        }
    }


}
