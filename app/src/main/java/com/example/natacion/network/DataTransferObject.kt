package com.example.natacion.network
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPostContainer(val posts: List<NetworkPost>)


@JsonClass(generateAdapter = true)
data class NetworkPost(
    var numero:Int,
    var titulo:String,
    var subtitulo:String ,
    var descripcion:String,
    var imagen:String,
    var audio:String,
)
