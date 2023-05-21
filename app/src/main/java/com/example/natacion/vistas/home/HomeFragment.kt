package com.example.natacion.vistas.home

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.natacion.R
import com.example.natacion.database.DataDatabase
import com.example.natacion.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val application = requireNotNull(this.activity).application
        val dataSource = DataDatabase.getInstance(application).dataDao
        ViewModelProvider(this, HomeViewModelFactory(dataSource, application))
            .get(HomeViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.getRegistros()
        homeViewModel.getDataUsuario()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        val adapter = HomeAdapter(HomeRegistroListener { registro ->
            var bundle = Bundle()
            registro.id?.let { bundle.putInt("id", it) }
            registro.numero?.let { bundle.putInt("numero", it) }
            bundle.putString("titulo", registro.titulo)
            bundle.putString("subtitulo", registro.subtitulo)
            bundle.putString("descripcion", registro.descripcion)
            bundle.putString("imagen", registro.imagen)
            bundle.putString("audio", registro.audio)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_homeFragment_to_verRegistrosFragment, bundle)
        }, homeViewModel)
        homeViewModel.registros.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.registros.adapter = adapter


        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.adminMenu.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            binding.drawerLayout.close()
            true
        }

        binding.adminMenu.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnSidePermisos -> {
                    NavHostFragment.findNavController(this)
                        .navigate(HomeFragmentDirections.actionHomeFragmentToAdminFragment())
                    false
                }
                R.id.btnSideEstadisticas -> {
                    val url = "https://console.firebase.google.com/u/5/project/natacion-8706f/analytics/app/android:com.example.natacion/overview/~2F%3Ft%3D1684698971502&fpn%3D382984184624&swu%3D1&sgu%3D1&cs%3Dapp.m.dashboard.overview&g%3D1" // Cambia esta URL por la que desees abrir

                    val alertDialogBuilder = AlertDialog.Builder(requireContext())
                    alertDialogBuilder.setTitle("Confirmación")
                    alertDialogBuilder.setMessage("¿Estás seguro de que deseas seguir el enlace, debe proporcionar las credenciales del administrador?")
                    alertDialogBuilder.setPositiveButton("Sí") { dialog, which ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }
                    alertDialogBuilder.setNegativeButton("No") { dialog, which ->
                        // El usuario no desea seguir el enlace, no se realiza ninguna acción
                    }

                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                    false
                }

                R.id.btnSideNuevoRegistro -> {
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_homeFragment_to_crearRegistrosFragment)
                    false
                }
                else -> {
                    false
                }
            }

        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btnMenuLogout -> {
                    homeViewModel.logOut()
                    true
                }
                else -> {
                    false
                }
            }
        }

        homeViewModel.logOutSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                NavHostFragment.findNavController(this)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToFragmentLogin())
            }
        })

        binding.btnBuscar.setOnClickListener {
            if (binding.spTipo.selectedItem.toString() == "Titulo") {
                val titulo = "%" + binding.txtBuscar.text.toString() + "%"
                if (!titulo.isNullOrEmpty()) {
                    homeViewModel.getRegistrosByTitulo(titulo)
                } else {
                    homeViewModel.getAllRegistros()
                }

            } else if (binding.spTipo.selectedItem.toString() == "Número") {
                try {
                    val numero = binding.txtBuscar.text.toString().toInt()
                    homeViewModel.getRegistrosByNumero(numero)
                } catch (e: java.lang.Exception) {
                    Log.d("Error", e.message.toString())
                    homeViewModel.getAllRegistros()
                }
            } else {
                homeViewModel.getRegistrosByFavoritos()
            }
            binding.txtBuscar.clearFocus()
        }

        homeViewModel.usuarioLogged.observe(viewLifecycleOwner, Observer { res ->
            if (res != null) {
                when (res.tipo) {
                    2 -> {

                    }
                    else -> {
                        binding.topAppBar.navigationIcon = null
                    }
                }
            } else {
                binding.topAppBar.navigationIcon = null
            }
        })

        homeViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.loadBlock.visibility = View.VISIBLE
            } else {
                binding.loadBlock.visibility = View.INVISIBLE
            }
        })

        homeViewModel.dataChange.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (binding.spTipo.selectedItem.toString() == "Titulo") {
                    val titulo = "%" + binding.txtBuscar.text.toString() + "%"
                    if (!titulo.isNullOrEmpty()) {
                        homeViewModel.getRegistrosByTitulo(titulo)
                    } else {
                        homeViewModel.getAllRegistros()
                    }

                } else if (binding.spTipo.selectedItem.toString() == "Número") {
                    try {
                        val numero = binding.txtBuscar.text.toString().toInt()
                        homeViewModel.getRegistrosByNumero(numero)
                    } catch (e: java.lang.Exception) {
                        Log.d("Error", e.message.toString())
                        homeViewModel.getAllRegistros()
                    }
                } else {
                    homeViewModel.getRegistrosByFavoritos()
                }
                homeViewModel.disableChange()
            }
        })


        return binding.root
    }

}