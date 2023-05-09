package com.example.natacion.vistas.loading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.natacion.R
import com.example.natacion.databinding.FragmentLoadingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoadingFragment : Fragment() {

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoadingBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_loading, container, false
        )
        val application = requireNotNull(this.activity).application

        val viewModelFactory = LoadingViewModelFactory(application)

        val loadingViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[LoadingViewModel::class.java]
        binding.lifecycleOwner = this
        binding.loadingViewModel = loadingViewModel

        auth = Firebase.auth
        val currentUser = auth.currentUser

        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)

            if (currentUser != null) {
                goToTitle()
            } else {
                goToLogin()
            }

        }

        return binding.root
    }

    private fun goToLogin() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_loadingFragment_to_fragmentLogin)
    }

    private fun goToTitle() {

    }

}