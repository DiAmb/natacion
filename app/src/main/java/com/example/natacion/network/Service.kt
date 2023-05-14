package com.example.natacion.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface DataService {
    @GET("registros")
    suspend fun getRegistros(): List<NetworkPost>
}

object RegistroNetwork {

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.105/datagen/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val registros = retrofit.create(DataService::class.java)

}