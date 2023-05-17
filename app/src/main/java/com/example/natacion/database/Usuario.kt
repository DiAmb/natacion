package com.example.natacion.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario")
data class Usuario(
    @PrimaryKey()
    @ColumnInfo(name = "usuario")
    var usuario: String = "",

    @ColumnInfo(name = "password")
    var password: String = "",

    @ColumnInfo(name = "tipo")
    var tipo: Boolean = false,
)