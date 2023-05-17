package com.example.natacion.network

import com.google.gson.annotations.SerializedName

data class NetworkRegistro(
    @SerializedName("numero")
    var numero:Int?,
    @SerializedName("titulo")
    var titulo:String?,
    @SerializedName("subtitulo")
    var subtitulo:String?,
    @SerializedName("descripcion")
    var descripcion:String?,
    @SerializedName("imagen")
    var imagen:String?,
    @SerializedName("audio")
    var audio:String?,
)
