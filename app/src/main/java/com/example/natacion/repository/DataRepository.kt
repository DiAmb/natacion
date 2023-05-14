package com.example.natacion.repository

import androidx.lifecycle.LiveData
import com.example.natacion.database.DataDao
import com.example.natacion.database.Registro
import com.example.natacion.network.RegistroNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private val database: DataDao) {

    suspend fun refreshRegistros() {
        withContext(Dispatchers.IO) {
            val registros = RegistroNetwork.registros.getRegistros()
            database.insertAll(registros.map {
                Registro(
                    numero = it.numero,
                    titulo = it.titulo,
                    subtitulo = it.subtitulo,
                    descripcion = it.descripcion,
                    imagen = it.imagen,
                    audio = it.audio,
                )
            })
        }
    }

}
