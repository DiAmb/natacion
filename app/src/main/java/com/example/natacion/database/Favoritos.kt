package com.example.natacion.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favoritos")
data class Favoritos(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id: Int?,
)