package com.example.natacion.vistas.registros

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class VerRegistrosViewModel(application: Application) :
    AndroidViewModel(application) {


    private val _showAdminOption = MutableLiveData<Boolean>()
    val showAdminOption: LiveData<Boolean> get() = _showAdminOption

    init {
        _showAdminOption.value = true;
    }

}