package com.example.natacion.vistas.crear

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.natacion.R
import com.example.natacion.database.DataDatabase
import com.example.natacion.database.Registro
import com.example.natacion.databinding.FragmentCrearRegistrosBinding
import java.io.IOException

class CrearRegistrosFragment : Fragment() {
    private val REQUEST_IMAGE = 2
    private lateinit var binding: FragmentCrearRegistrosBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var TiempoRestanteAudio: TextView
    private lateinit var TiempoReproducidoAudio: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_crear_registros, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = DataDatabase.getInstance(application).dataDao

        val viewModelFactory = CrearRegistrosViewModelFactory(dataSource, application)

        val crearRegistrosViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[CrearRegistrosViewModel::class.java]
        binding.lifecycleOwner = this
        binding.crearRegistrosViewModel = crearRegistrosViewModel

        binding.topAppBar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnMenuGuardar -> {
                    context?.let { it1 -> hidekeyboard(it1) }
                    val registro = Registro(
                        0,
                        binding.editNumero.text.toString().toInt(),
                        binding.editTitulo.text.toString(),
                        binding.editSubtitulo.text.toString(),
                        binding.editDescripcion.text.toString(),
                        binding.editImagen.text.toString(),
                        binding.editAudio.text.toString()
                    )
                    crearRegistrosViewModel.insertRegistro(
                        registro
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }



        crearRegistrosViewModel.backHome.observe(viewLifecycleOwner, Observer {
            if (it) {
                NavHostFragment.findNavController(this).popBackStack()
            }
        })

        crearRegistrosViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.loadBlock.visibility = View.VISIBLE
            } else {
                binding.loadBlock.visibility = View.INVISIBLE
            }
        })


        return binding.root
    }
    private fun hidekeyboard(context: Context){
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken  , 0)


    }

}