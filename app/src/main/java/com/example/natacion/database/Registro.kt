package com.example.natacion.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Registro")
data class Registro(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "numero")
    @SerializedName("numero")
    @Expose
    var numero: Int? = null,

    @ColumnInfo(name = "titulo")
    @SerializedName("titulo")
    @Expose
    var titulo: String? = null,

    @ColumnInfo(name = "subtitulo")
    @SerializedName("subtitulo")
    @Expose
    var subtitulo: String? = null,

    @ColumnInfo(name = "descripcion")
    @SerializedName("descripcion")
    @Expose
    var descripcion: String? = null,

    @ColumnInfo(name = "imagen")
    @SerializedName("imagen")
    @Expose
    var imagen: String? = null,

    @ColumnInfo(name = "audio")
    @SerializedName("audio")
    @Expose
    var audio: String? = null,
)