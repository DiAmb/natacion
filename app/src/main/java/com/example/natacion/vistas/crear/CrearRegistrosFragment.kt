package com.example.natacion.vistas.crear

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.natacion.R
import com.example.natacion.databinding.FragmentCrearRegistrosBinding
import com.example.natacion.databinding.FragmentVerRegistrosBinding
import com.example.natacion.vistas.registros.VerRegistrosViewModel
import com.example.natacion.vistas.registros.VerRegistrosViewModelFactory

class CrearRegistrosFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding: FragmentCrearRegistrosBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_crear_registros, container, false
        )
        val application = requireNotNull(this.activity).application

        val viewModelFactory = CrearRegistrosViewModelFactory(application)

        val crearRegistrosViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[CrearRegistrosViewModel::class.java]
        binding.lifecycleOwner = this
        binding.crearRegistrosViewModel = crearRegistrosViewModel

        binding.btnRegresar.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        return binding.root
    }

}