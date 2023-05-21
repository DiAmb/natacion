package com.example.natacion.vistas.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.natacion.R
import com.example.natacion.database.DataDatabase
import com.example.natacion.databinding.FragmentLoginBinding


class FragmentLogin : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = DataDatabase.getInstance(application).dataDao
        val viewModelFactory = LoginViewModelFactory(dataSource, application)


        val loginViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[LoginViewModel::class.java]
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel

        binding.btnRegistrar.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(FragmentLoginDirections.actionFragmentLoginToRegistrarseFragment())
        }

        binding.btnAcceder.setOnClickListener {
            var email: String = binding.txtCorreo.text.toString()
            var password: String = binding.txtPassword.text.toString()
            context?.let { it1 -> hidekeyboard(it1) }
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                loginViewModel.accederUsuario(email, password)
            } else {
                Toast.makeText(
                    context,
                    "Ingrese todos los campos para poder continuar.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        loginViewModel.loginSucess.observe(viewLifecycleOwner, Observer { res ->
            when (res) {
                -1 -> {
                    Toast.makeText(context, "Login fallido.", Toast.LENGTH_SHORT).show()
                    loginViewModel.resetCodeLogin()
                }
                0 -> {
                    Toast.makeText(
                        context,
                        "No cuenta con autorización para acceder a la aplicación.",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginViewModel.resetCodeLogin()
                }
                1 -> {
                    loginViewModel.resetCodeLogin()
                    goToHome()
                }
            }
        })

        loginViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.loadBlock.visibility = View.VISIBLE
            } else {
                binding.loadBlock.visibility = View.INVISIBLE
            }
        })


        return binding.root
    }

    private fun goToHome() {
        NavHostFragment.findNavController(this).navigate(R.id.action_fragmentLogin_to_homeFragment)
    }
    private fun hidekeyboard(context: Context){
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken  , 0)


    }


}