package com.example.natacion.vistas.admin

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
import com.example.natacion.database.DataDatabase
import com.example.natacion.databinding.FragmentAdminBinding


class AdminFragment : Fragment() {

    private val adminViewModel: AdminViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val application = requireNotNull(this.activity).application
        val dataSource = DataDatabase.getInstance(application).dataDao
        ViewModelProvider(this, AdminViewModelFactory(dataSource, application))
            .get(AdminViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAdminBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_admin, container, false
        )

        binding.lifecycleOwner = this
        binding.adminViewModel = adminViewModel

        val adapter = AdminAdapter(adminViewModel)
        binding.usuarios.adapter = adapter

        adminViewModel.usuarios.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.topAppBar.setNavigationOnClickListener { menuItem ->
            // Handle menu item selected
            NavHostFragment.findNavController(this).popBackStack()
            true
        }


        adminViewModel.usuarioLogged.observe(viewLifecycleOwner, Observer {
            it?.let {
                adminViewModel.getUsuarios(it)
            }
        })

        adminViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.loadBlock.visibility = View.VISIBLE
            } else {
                binding.loadBlock.visibility = View.INVISIBLE
            }
        })

        return binding.root
    }


}