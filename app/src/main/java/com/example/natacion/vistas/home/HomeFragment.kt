package com.example.natacion.vistas.home

import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = DataDatabase.getInstance(application).dataDao

        val viewModelFactory = HomeViewModelFactory(dataSource,application)

        val homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        val adapter = HomeAdapter(HomeRegistroListener { registro ->
            var bundle = Bundle()
            registro.numero?.let { bundle.putInt("numero", it) }
            bundle.putString("titulo", registro.titulo)
            bundle.putString("descripcion", registro.descripcion)
            bundle.putString("imagen", registro.imagen)
            bundle.putString("audio", registro.audio)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_homeFragment_to_verRegistrosFragment, bundle)
        })
        homeViewModel.registros.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.registros.adapter = adapter



        binding.btnCrear.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_homeFragment_to_crearRegistrosFragment)
        }

        return binding.root
    }

}