package com.example.natacion.vistas.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.natacion.R
import com.example.natacion.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = HomeViewModelFactory(application)

        val homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        val adapter = HomeAdapter(HomeRegistroListener { numero ->

        })

        binding.registros.adapter = adapter


        return binding.root
    }

}