package com.example.natacion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Query("select * from Registro")
    suspend fun getRegistros(): List<Registro>

    @Query("select * from Registro where titulo LIKE :titulo")
    suspend fun getRegistrosByTitulo(titulo: String): List<Registro>

    @Query("select * from Registro where numero = :numero")
    suspend fun getRegistrosByNumero(numero: Int): List<Registro>

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

    //FAVORITOS
    @Query("DELETE FROM Favoritos")
    suspend fun deleteAllFavoritos()

    @Query("DELETE FROM Favoritos where id = :id")
    suspend fun deleteFavorito(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorito(favorito: Favoritos)

    @Query("select * from Favoritos")
    suspend fun getFavoritos(): List<Favoritos>

    @Query("select * from Favoritos where id = :id limit 1")
    suspend fun getFavorito(id: Int): Favoritos

    @Query("SELECT * FROM Registro WHERE id IN (:ids)")
    suspend fun getRegistrosFavoritos(ids: ArrayList<Int>): List<Registro>

}