package com.example.natacion.vistas.crear

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.natacion.R
import com.example.natacion.databinding.FragmentCrearRegistrosBinding
import com.example.natacion.databinding.FragmentVerRegistrosBinding
import com.example.natacion.vistas.registros.VerRegistrosViewModel
import com.example.natacion.vistas.registros.VerRegistrosViewModelFactory
import java.io.IOException
import kotlin.math.log

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
        binding.btnMusica.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            startActivityForResult(intent, 1)
        }
        TiempoReproducidoAudio = binding.timeCurrent
        TiempoRestanteAudio = binding.timeLeft
        mediaPlayer = MediaPlayer()
        binding.progresoAudio.max = 0
        binding.progresoAudio.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.seekTo(seekBar?.progress ?: 0)
            }
        })
        binding.btnPlay.setOnClickListener {
            togglePlayback()
        }
        binding.btnSeleccionar.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE)
        }





        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val audioUri = data?.data
            if (audioUri != null) {
                try {
                    if(mediaPlayer.isPlaying){
                        mediaPlayer.stop()
                    }
                    // Código para la API
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(requireContext(), audioUri) // Configurar la fuente de audio en el objeto MediaPlayer
                    mediaPlayer.prepareAsync()
                    mediaPlayer.setOnPreparedListener { mediaPlayer.start()
                    binding.btnPlay.visibility = View.VISIBLE
                    binding.progresoAudio.max = mediaPlayer.duration
                    setupSeekBar()
                    }
                } catch (e: IOException) {
                    Toast.makeText(requireContext(), "Error al establecer la fuente de audio", Toast.LENGTH_SHORT).show()
                } catch (e: IllegalStateException) {
                    Toast.makeText(requireContext(), "El reproductor multimedia no se encuentra en el estado adecuado", Toast.LENGTH_SHORT).show()
                } catch (e: SecurityException) {
                    Toast.makeText(requireContext(), "Permiso denegado para acceder a la fuente de audio", Toast.LENGTH_SHORT).show()
                } catch (e: IllegalArgumentException) {
                    Toast.makeText(requireContext(), "Argumento ilegal pasado al reproductor multimedia", Toast.LENGTH_SHORT).show()
                }
            }
            binding.btnPlay.setBackgroundResource(R.drawable.ic_play)
        }else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

            val imageUri = data?.data
            if (imageUri != null) {
                try {
                    binding.mostrarImagen.setImageURI(imageUri)
                    // Código para la API
                } catch (e: IOException) {
                    Toast.makeText(requireContext(), "Error al establecer la fuente de imagen", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun setupSeekBar() {
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                val tiempoReproducido = mediaPlayer.currentPosition / 1000
                val tiempoRestante = (mediaPlayer.duration - mediaPlayer.currentPosition) / 1000
                val tiempoReproducidoStr = String.format("%02d:%02d", tiempoReproducido / 60, tiempoReproducido % 60)
                val tiempoRestanteStr = String.format("%02d:%02d", tiempoRestante / 60, tiempoRestante % 60)
                TiempoReproducidoAudio.text = tiempoReproducidoStr
                TiempoRestanteAudio.text = tiempoRestanteStr

                binding.progresoAudio.progress = mediaPlayer.currentPosition
                handler.postDelayed(this, 1000)
            }
        }, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun togglePlayback() {
        if (mediaPlayer.isPlaying) {
            binding.btnPlay.setBackgroundResource(R.drawable.ic_pause)
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
            binding.btnPlay.setBackgroundResource(R.drawable.ic_play)
        }
    }
}