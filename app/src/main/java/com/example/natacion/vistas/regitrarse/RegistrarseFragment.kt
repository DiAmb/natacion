package com.example.natacion.vistas.regitrarse

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.natacion.R
import com.example.natacion.database.DataDatabase
import com.example.natacion.databinding.FragmentRegistrarseBinding


class RegistrarseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegistrarseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_registrarse, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = DataDatabase.getInstance(application).dataDao
        val viewModelFactory = RegistrarseViewModelFactory(dataSource, application)


        val registrarseViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[RegistrarseViewModel::class.java]
        binding.lifecycleOwner = this
        binding.registrarseViewModel = registrarseViewModel

        binding.topAppBar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        binding.btnRegistrar.setOnClickListener {
            context?.let { it1 -> hidekeyboard(it1) }
            var email: String = binding.editCorreo.text.toString()
            var password: String = binding.editPassword.text.toString()
            var nombre: String = binding.editNombres.text.toString()
            var apellidos: String = binding.editApellidos.text.toString()

            if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !nombre.isNullOrEmpty() && !apellidos.isNullOrEmpty()) {
                registrarseViewModel.registrarUsuario(
                    email,
                    password,
                    nombre,
                    apellidos
                )
            } else {
                Toast.makeText(
                    context,
                    "Ingrese todos los campos para poder continuar.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


        registrarseViewModel.sendSuccess.observe(viewLifecycleOwner, Observer { res ->
            if (res) {
                Toast.makeText(
                    context,
                    "Solicitud de acceso enviada, espere su autorización.",
                    Toast.LENGTH_LONG
                ).show()
                goToHome()
            }
        })

        registrarseViewModel.sendFailed.observe(viewLifecycleOwner, Observer { res ->
            if (res) {
                Toast.makeText(
                    context,
                    "Registro fallido, comprueba que el correo electronico no se haya usado en una petición anterior",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        registrarseViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.loadBlock.visibility = View.VISIBLE
            } else {
                binding.loadBlock.visibility = View.INVISIBLE
            }
        })


        return binding.root
    }

    private fun goToHome() {
        NavHostFragment.findNavController(this).popBackStack()
    }
    private fun hidekeyboard(context: Context){
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken  , 0)


    }
}