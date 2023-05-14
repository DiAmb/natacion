package com.example.natacion.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Registro")
data class Registro(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "numero")
    var numero:Int = 0,

    @ColumnInfo(name = "titulo")
    var titulo:String ="",

    @ColumnInfo(name = "subtitulo")
    var subtitulo:String ="",

    @ColumnInfo(name = "descripcion")
    var descripcion:String ="",

    @ColumnInfo(name = "imagen")
    var imagen:String ="",

    @ColumnInfo(name = "audio")
    var audio:String ="",
)