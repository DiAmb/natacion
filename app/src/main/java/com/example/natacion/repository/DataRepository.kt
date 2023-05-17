package com.example.natacion.repository

import android.util.Log
import com.example.natacion.database.DataDao
import com.example.natacion.network.RegistroNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "BACKEND ACTION"

class DataRepository(private val database: DataDao) {

    suspend fun refreshRegistros() {
        withContext(Dispatchers.IO) {
            try {
                val registros = RegistroNetwork.registros.getRegistros()
                if (registros != null) {
                    database.deleteAll()
                    database.insertAll(registros)
                } else {

                }
            } catch (e: java.lang.Exception) {
                Log.d(TAG, e.message.toString())
            }

        }
    }

}
