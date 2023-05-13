package com.example.natacion.vistas.registros

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.natacion.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.natacion.databinding.FragmentVerRegistrosBinding

class VerRegistrosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentVerRegistrosBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_ver_registros, container, false
        )
        val application = requireNotNull(this.activity).application

        val viewModelFactory = VerRegistrosViewModelFactory(application)

        val verRegistrosViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[VerRegistrosViewModel::class.java]
        binding.lifecycleOwner = this
        binding.verRegistrosViewModel = verRegistrosViewModel

        var numero = arguments?.getInt("numero")
        var titulo = arguments?.getString("titulo")
        var descripcion = arguments?.getString("descripcion")
        var imagen = arguments?.getString("imagen")
        var audio = arguments?.getString("audio")


        binding.txtTitulo.text = numero.toString() + " " + titulo
        binding.txtSubtitulo.text = descripcion

        binding.btnRegresar.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        binding.btnEditar.setOnClickListener {
            var bundle = Bundle()
            if (numero != null) {
                bundle.putInt("numero", numero)
            }
            bundle.putString("titulo", titulo)
            bundle.putString("descripcion", descripcion)
            bundle.putString("imagen", imagen)
            bundle.putString("audio", audio)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_verRegistrosFragment_to_editarRegistrosFragment, bundle)
        }

        binding.btnEliminar.setOnClickListener {
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton("Borrar",
                        DialogInterface.OnClickListener { dialog, id ->
                            Toast.makeText(context, "Eliminando", Toast.LENGTH_SHORT).show()
                        })
                    setNegativeButton("Cancelar",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled th
                        })
                }
                // Set other dialog properties
                builder?.setMessage("¿Deseas borrar este registro?")
                    ?.setTitle("Alerta")

                // Create the AlertDialog
                builder.create()
            }
            alertDialog?.show()
        }
        verRegistrosViewModel.showAdminOption.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.btnEliminar.visibility = View.VISIBLE
                binding.btnEditar.visibility = View.VISIBLE
            }
        })

        return binding.root
    }
}