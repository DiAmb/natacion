package com.example.natacion.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity(tableName = "Registro")
data class Registro(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "numero")
    var numero: Int?,

    @ColumnInfo(name = "titulo")
    var titulo: String,

    @ColumnInfo(name = "subtitulo")
    var subtitulo: String,

    @ColumnInfo(name = "descripcion")
    var descripcion: String,

    @ColumnInfo(name = "descripciondos")
    var descripciondos: String,

    @ColumnInfo(name = "imagen")
    var imagen: String,

    @ColumnInfo(name = "audio")
    var audio: String
)
