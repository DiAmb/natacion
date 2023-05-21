package com.example.natacion.vistas.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.natacion.database.Registro
import com.example.natacion.databinding.RegistroBinding
import com.example.natacion.vistas.admin.AdminViewModel

class HomeAdapter(val clickListener: HomeRegistroListener, val homeViewModel: HomeViewModel) :
    ListAdapter<Registro, HomeAdapter.ViewHolder>(HomeDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, homeViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RegistroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Registro,
            clickListener: HomeRegistroListener,
            homeViewModel: HomeViewModel
        ) {
            binding.registro = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
            binding.btnFavorito.setOnClickListener { boton ->
                item.id?.let {
                    homeViewModel.insertFavoritos(it, boton.context, item.titulo)
                }

            }
            var url = item.imagen
            Glide.with(binding.root).load(url).into(binding.imgRegistro);
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RegistroBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class HomeDiffCallback : DiffUtil.ItemCallback<Registro>() {

    override fun areItemsTheSame(oldItem: Registro, newItem: Registro): Boolean {
        return oldItem.numero == newItem.numero
    }

    override fun areContentsTheSame(oldItem: Registro, newItem: Registro): Boolean {
        return oldItem == newItem
    }
}


class HomeRegistroListener(val clickListener: (registro: Registro) -> Unit) {
    fun onClick(registro: Registro) = clickListener(registro)
}

