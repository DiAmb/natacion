package com.example.natacion.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Query("select * from Registro")
    suspend fun getRegistros(): List<Registro>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(registros: List<Registro>)
}