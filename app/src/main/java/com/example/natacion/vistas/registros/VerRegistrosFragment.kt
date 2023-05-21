package com.example.natacion.vistas.registros

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.natacion.R
import com.example.natacion.database.DataDatabase
import com.example.natacion.database.Registro
import com.example.natacion.databinding.FragmentCrearRegistrosBinding
import com.example.natacion.databinding.FragmentVerRegistrosBinding
import java.io.IOException


class VerRegistrosFragment : Fragment() {
    private lateinit var binding: FragmentVerRegistrosBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var TiempoRestanteAudio: TextView
    private lateinit var TiempoReproducidoAudio: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_ver_registros, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = DataDatabase.getInstance(application).dataDao

        val viewModelFactory = VerRegistrosViewModelFactory(dataSource, application)

        val verRegistrosViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[VerRegistrosViewModel::class.java]
        binding.lifecycleOwner = this
        binding.verRegistrosViewModel = verRegistrosViewModel

        var id = arguments?.getInt("id")
        var numero = arguments?.getInt("numero")
        var titulo = arguments?.getString("titulo")
        var subtitulo = arguments?.getString("subtitulo")
        var descripcion = arguments?.getString("descripcion")
        var imagen = arguments?.getString("imagen")
        var audio = arguments?.getString("audio")
        mediaPlayer = MediaPlayer()

        Glide.with(binding.root).load(imagen).into(binding.imageView);

        binding.txtTitulo.text = numero.toString() + " " + titulo
        binding.txtSubtitulo.text = subtitulo
        binding.txtDescripcion.text = descripcion
        TiempoReproducidoAudio = binding.timeCurrent
        TiempoRestanteAudio = binding.timeLeft
        binding.topAppBar.title = numero.toString() + " " + titulo

        if (!mediaPlayer.isPlaying) {
            if (!audio.isNullOrEmpty()) {

                try {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                    }
                    // Código para la API
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(
                        audio
                    ) // Configurar la fuente de audio en el objeto MediaPlayer
                    mediaPlayer.prepareAsync()
                    mediaPlayer.setOnPreparedListener {
                        mediaPlayer.start()
                        binding.btnPlay.visibility = View.VISIBLE
                        binding.progresoAudio.max = mediaPlayer.duration
                        setupSeekBar()
                    }
                    binding.btnPlay.setBackgroundResource(R.drawable.ic_pause)
                } catch (e: IOException) {
                    Toast.makeText(
                        requireContext(),
                        "Error al establecer la fuente de audio",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this.context, "Direccion url invalida", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnPlay.setOnClickListener {
//            if(!audio.isNullOrEmpty()){
//                if(mediaPlayer.isPlaying){
//                    mediaPlayer.stop()
//                    mediaPlayer.reset()
//                    mediaPlayer.release()
//                    binding.btnPlay.setBackgroundResource(R.drawable.ic_play)
//                }else{
//                    playAudio(audio)
//                    binding.btnPlay.setBackgroundResource(R.drawable.ic_pause)
//                }
//
//
//            }else{
//                Toast.makeText(this.context, "Direccion url invalida", Toast.LENGTH_SHORT).show()
//            }

            //--------

            //----

//            val audioUri = data?.data
//            if (audioUri != null) {
//                try {
//                    if (mediaPlayer.isPlaying) {
//                        mediaPlayer.stop()
//                    }
//                    // Código para la API
//                    mediaPlayer.reset()
//                    mediaPlayer.setDataSource(
//                        requireContext(),
//                        audioUri
//                    ) // Configurar la fuente de audio en el objeto MediaPlayer
//                    mediaPlayer.prepareAsync()
//                    mediaPlayer.setOnPreparedListener {
//                        mediaPlayer.start()
//                        binding.btnPlay.visibility = View.VISIBLE
//                        binding.progresoAudio.max = mediaPlayer.duration
//                        setupSeekBar()
//                    }
//                    binding.btnPlay.setBackgroundResource(R.drawable.ic_pause)
//                } catch (e: IOException) {
//                    Toast.makeText(
//                        requireContext(),
//                        "Error al establecer la fuente de audio",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } catch (e: IllegalStateException) {
//                    Toast.makeText(
//                        requireContext(),
//                        "El reproductor multimedia no se encuentra en el estado adecuado",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } catch (e: SecurityException) {
//                    Toast.makeText(
//                        requireContext(),
//                        "Permiso denegado para acceder a la fuente de audio",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } catch (e: IllegalArgumentException) {
//                    Toast.makeText(
//                        requireContext(),
//                        "Argumento ilegal pasado al reproductor multimedia",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
            togglePlayback()


        }
        binding.progresoAudio.max = 0

        binding.progresoAudio.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.seekTo(seekBar?.progress ?: 0)
            }
        })

        binding.topAppBar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        verRegistrosViewModel.completeDelete.observe(viewLifecycleOwner, Observer {
            if (it) {
                NavHostFragment.findNavController(this).popBackStack()
            }
        })

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnMenuRegistroEditar -> {
                    var bundle = Bundle()
                    if (id != null) {
                        bundle.putInt("id", id)
                    }
                    if (numero != null) {
                        bundle.putInt("numero", numero)
                    }

                    bundle.putString("titulo", titulo)
                    bundle.putString("subtitulo", subtitulo)
                    bundle.putString("descripcion", descripcion)
                    bundle.putString("imagen", imagen)
                    bundle.putString("audio", audio)
                    NavHostFragment.findNavController(this)
                        .navigate(
                            R.id.action_verRegistrosFragment_to_editarRegistrosFragment,
                            bundle
                        )
                    true
                }
                R.id.btnMenuRegistroEliminar -> {
                    val alertDialog: AlertDialog? = activity?.let {
                        val builder = AlertDialog.Builder(it)
                        builder.apply {
                            setPositiveButton("Borrar",
                                DialogInterface.OnClickListener { dialog, iden ->
                                    if (id != null) {
                                        verRegistrosViewModel.deleteRegistro(id)
                                    }
                                })
                            setNegativeButton("Cancelar",
                                DialogInterface.OnClickListener { dialog, iden ->
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
                    true
                }
                else -> {
                    false
                }
            }
        }

        verRegistrosViewModel.usuarioLogged.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.tipo) {
                    0 -> {
                        binding.topAppBar.menu.get(0).setVisible(false)
                        binding.topAppBar.menu.get(1).setVisible(false)
                    }
                    1 -> {
                        binding.topAppBar.menu.get(0).setVisible(true)
                        binding.topAppBar.menu.get(1).setVisible(false)
                    }
                    2 -> {
                        binding.topAppBar.menu.get(0).setVisible(true)
                        binding.topAppBar.menu.get(1).setVisible(true)
                    }
                }
            } else {
                binding.topAppBar.menu.get(0).setVisible(false)
                binding.topAppBar.menu.get(1).setVisible(false)
            }
        })

        verRegistrosViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.loadBlock.visibility = View.VISIBLE
            } else {
                binding.loadBlock.visibility = View.INVISIBLE
            }
        })


        return binding.root
    }

    private fun playAudio(audio: String) {
        val audioUrl = audio
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            mediaPlayer.setDataSource(audioUrl)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this.context, "Archivo invalido", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this.context, "Audio en reproduccion...", Toast.LENGTH_SHORT).show()
    }

    private fun setupSeekBar() {
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    val tiempoReproducido = mediaPlayer.currentPosition / 1000
                    val tiempoRestante = (mediaPlayer.duration - mediaPlayer.currentPosition) / 1000
                    val tiempoReproducidoStr =
                        String.format("%02d:%02d", tiempoReproducido / 60, tiempoReproducido % 60)
                    val tiempoRestanteStr =
                        String.format("%02d:%02d", tiempoRestante / 60, tiempoRestante % 60)
                    TiempoReproducidoAudio.text = tiempoReproducidoStr
                    TiempoRestanteAudio.text = tiempoRestanteStr
                    binding.progresoAudio.progress = mediaPlayer.currentPosition

                    handler.postDelayed(this, 1000)
                } catch (e: IllegalStateException) {
                    handler.removeCallbacks(this)
                }
            }
        }, 0)
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.stop()
    }


    private fun togglePlayback() {
        if (mediaPlayer.isPlaying) {
            binding.btnPlay.setBackgroundResource(R.drawable.ic_play)
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
            binding.btnPlay.setBackgroundResource(R.drawable.ic_pause)
        }
    }
}

