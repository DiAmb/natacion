package com.example.natacion.network


import com.example.natacion.database.Registro
import com.example.natacion.database.Usuario
import com.example.natacion.database.UsuarioResponse
import com.example.natacion.repository.DataRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface DataService {
    @GET("registros/")
    suspend fun getRegistros(): List<Registro>

    @POST("registros/")
    suspend fun insertRegistro(@Body registro: Registro): Response<Registro>

    @POST("register/")
    suspend fun registrarUsuario(@Body usuario: Usuario): Response<UsuarioResponse>

    @POST("login/")
    suspend fun loginUsuario(@Body usuario: Usuario): Response<UsuarioResponse>
}

object RegistroNetwork {

    private val client = OkHttpClient.Builder()
        .build()

    private val moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()


    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.105/datagen/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val registros = retrofit.create(DataService::class.java)

}