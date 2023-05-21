package com.example.natacion.network


import com.example.natacion.database.Registro
import com.example.natacion.database.Usuario
import com.example.natacion.database.UsuarioDelete
import com.example.natacion.database.UsuarioUpdate
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.ResponseBody
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface DataService {
    @GET("registros/registros.php/")
    suspend fun getRegistros(): List<Registro>

    @GET("registros/registros.php/")
    suspend fun getRegistrosByNumero(@Query("numero") numero: Int): List<Registro>

    @GET("registros/registros.php/")
    suspend fun getRegistrosByTitulo(@Query("titulo") numero: String): List<Registro>

    @POST("registros/registros.php/")
    suspend fun insertRegistro(@Body registro: Registro): Response<Registro>

    @PUT("registros/registros.php/")
    suspend fun updateRegistro(@Body registro: Registro): Response<Registro>

    @DELETE("registros/registros.php/")
    suspend fun deleteRegistro(@Query("id") numero: Int): Response<ResponseBody>

    @POST("register/usuario.php/")
    suspend fun registrarUsuario(@Body usuario: Usuario): Response<Usuario>

    @POST("login/usuario.php/")
    suspend fun loginUsuario(@Body usuario: Usuario): Response<Usuario>

    @POST("admin/usuario.php/")
    suspend fun getUsuarios(@Body usuario: Usuario): Response<List<Usuario>>

    @PUT("admin/usuario.php/")
    suspend fun updateUsuario(@Body data: UsuarioUpdate): Response<List<Usuario>>

    @HTTP(method = "DELETE", hasBody = true, path = "admin/usuario.php/")
    suspend fun deleteUsuario(@Body data: UsuarioDelete): Response<List<Usuario>>


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
        .baseUrl("https://datagen.ingsistemascunori.org/datagen/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val registros = retrofit.create(DataService::class.java)

}