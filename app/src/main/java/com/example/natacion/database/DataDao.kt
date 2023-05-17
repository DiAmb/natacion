package com.example.natacion.database

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


    @Query("SELECT * from Usuario limit 1")
    suspend fun isLogged(): Usuario

    @Query("DELETE FROM Usuario")
    suspend fun deleteAllUsuario()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserUsuario(usuario: Usuario)

    @Query("DELETE FROM Registro")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegistro(registro: Registro)
}