package com.example.natacion.vistas.admin

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.natacion.R
import com.example.natacion.database.Usuario
import com.example.natacion.databinding.UsuarioBinding


class AdminAdapter(val adminViewModel: AdminViewModel) :
    ListAdapter<Usuario, AdminAdapter.ViewHolder>(
        ViewListDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, adminViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: UsuarioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Usuario, adminViewModel: AdminViewModel) {
            binding.usuario = item
            binding.adminViewModel = adminViewModel
            when (item.tipo) {
                -1 -> {
                    binding.btnDelete.visibility = View.VISIBLE
                    binding.rbSinAccesso.isChecked = true
                }
                0 -> {
                    binding.btnDelete.visibility = View.VISIBLE
                    binding.rbLector.isChecked = true
                }
                1 -> {
                    binding.btnDelete.visibility = View.VISIBLE
                    binding.rbEditor.isChecked = true
                }
                2 -> {/*
                    binding.chkGroup.isEnabled = false
                    binding.rbAdministrador.isEnabled = false
                    binding.rbEditor.isEnabled = false
                    binding.rbLector.isEnabled = false
                    binding.rbSinAccesso.isEnabled = false*/
                    binding.btnDelete.visibility = View.INVISIBLE
                    binding.rbAdministrador.isChecked = true
                }
            }
            binding.txtCorreo.text = item.correo
            binding.txtNombre.text = item.nombres + " " + item.apellidos
            binding.chkGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.rbSinAccesso -> {
                        item.tipo = -1
                        binding.btnDelete.visibility = View.VISIBLE
                        adminViewModel.updateUsuario(item)
                    }
                    R.id.rbLector -> {
                        item.tipo = -0
                        binding.btnDelete.visibility = View.VISIBLE
                        adminViewModel.updateUsuario(item)
                    }
                    R.id.rbEditor -> {
                        item.tipo = 1
                        binding.btnDelete.visibility = View.VISIBLE
                        adminViewModel.updateUsuario(item)
                    }
                    R.id.rbAdministrador -> {
                        item.tipo = 2
                        binding.btnDelete.visibility = View.INVISIBLE
                        adminViewModel.updateUsuario(item)
                    }
                }
            }

            binding.btnDelete.setOnClickListener {
                val alertDialog: AlertDialog? = it.context?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setPositiveButton("Eliminar",
                            DialogInterface.OnClickListener { dialog, iden ->
                                adminViewModel.deleteUsuario(item.correo)
                            })
                        setNegativeButton("Cancelar",
                            DialogInterface.OnClickListener { dialog, iden ->
                                // User cancelled th
                            })
                    }
                    // Set other dialog properties
                    builder?.setMessage("¿Seguro que desea eliminar a " + item.correo + "?")
                        ?.setTitle("Alerta")

                    // Create the AlertDialog
                    builder.create()
                }
                alertDialog?.show()
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    UsuarioBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ViewListDiffCallback : DiffUtil.ItemCallback<Usuario>() {

    override fun areItemsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
        return oldItem.correo == newItem.correo
    }

    override fun areContentsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
        return oldItem == newItem
    }
}
