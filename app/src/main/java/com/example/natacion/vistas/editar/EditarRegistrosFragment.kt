package com.example.natacion.vistas.editar

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
import com.example.natacion.databinding.FragmentEditarRegistrosBinding

class EditarRegistrosFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding: FragmentEditarRegistrosBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_editar_registros, container, false
        )
        val application = requireNotNull(this.activity).application

        val viewModelFactory = EditarRegistrosViewModelFactory(application)

        val editarRegistrosViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[EditarRegistrosViewModel::class.java]
        binding.lifecycleOwner = this
        binding.editarRegistrosViewModel = editarRegistrosViewModel

        var numero = arguments?.getInt("numero")
        var titulo = arguments?.getString("titulo")
        var descripcion = arguments?.getString("descripcion")
        var imagen = arguments?.getString("imagen")
        var audio = arguments?.getString("audio")


        binding.editTitulo.setText(numero.toString() + " " + titulo)
        binding.editDescripcion.setText(descripcion)

        binding.btnRegresar.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        return binding.root
    }

}