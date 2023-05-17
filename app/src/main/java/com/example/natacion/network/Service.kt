package com.example.natacion.network

import com.example.natacion.database.Registro
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.lang.reflect.Type

class UrlDeserializer : JsonDeserializer<HttpUrl> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): HttpUrl =
        HttpUrl.get(json.asString)

}

interface DataService {
    @GET("registros")
    suspend fun getRegistros(): List<NetworkRegistro>


    @POST("setregistro")
    suspend fun insertRegistro(@Body registro: RequestBody): Response<ResponseBody>
}

object RegistroNetwork {

    /*val gson = GsonBuilder()
        .registerTypeAdapter()
        .create()*/

    private val client = OkHttpClient.Builder()
        .build()

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.105/datagen/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val registros = retrofit.create(DataService::class.java)

}