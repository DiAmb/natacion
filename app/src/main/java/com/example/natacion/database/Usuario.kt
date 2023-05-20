package com.example.natacion.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario")
data class Usuario(
    @PrimaryKey()
    @ColumnInfo(name = "correo")
    var correo: String = "",

    @ColumnInfo(name = "password")
    var password: String = "",

    @ColumnInfo(name = "tipo")
    var tipo: Int = 0,

    @ColumnInfo(name = "nombres")
    var nombres: String = "",

    @ColumnInfo(name = "apellidos")
    var apellidos: String = "",
)