package com.example.natacion.vistas.editar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.natacion.R
import com.example.natacion.database.Registro
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

        var id = arguments?.getInt("id")
        var numero = arguments?.getInt("numero")
        var titulo = arguments?.getString("titulo")
        var descripcion = arguments?.getString("descripcion")
        var subtitulo = arguments?.getString("subtitulo")
        var imagen = arguments?.getString("imagen")
        var audio = arguments?.getString("audio")


        binding.editTitulo.setText(titulo)
        binding.editDescripcion.setText(descripcion)
        binding.editAudio.setText(audio)
        binding.editImagen.setText(imagen)
        binding.editSubtitulo.setText(subtitulo)
        binding.editNumero.setText(numero.toString())

        binding.topAppBar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnMenuGuardar -> {
                    val registro = Registro(
                        id,
                        binding.editNumero.text.toString().toInt(),
                        binding.editTitulo.text.toString(),
                        binding.editSubtitulo.text.toString(),
                        binding.editDescripcion.text.toString(),
                        binding.editImagen.text.toString(),
                        binding.editAudio.text.toString()
                    )
                    editarRegistrosViewModel.updateRegistro(
                        registro
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }

        editarRegistrosViewModel.backHome.observe(viewLifecycleOwner, Observer {
            if (it) {
                var bundle = Bundle()
                id?.let { bundle.putInt("id", it) }
                numero?.let { bundle.putInt("numero", it) }
                bundle.putString("titulo", binding.editTitulo.text.toString())
                bundle.putString("subtitulo", binding.editSubtitulo.text.toString())
                bundle.putString("descripcion", binding.editDescripcion.text.toString())
                bundle.putString("imagen", binding.editImagen.text.toString())
                bundle.putString("audio", binding.editAudio.text.toString())
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_editarRegistrosFragment_to_verRegistrosFragment, bundle)
            }
        })

        return binding.root
    }

}