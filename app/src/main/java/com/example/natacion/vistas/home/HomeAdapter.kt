package com.example.natacion.vistas.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.natacion.databinding.RegistroBinding
import com.example.natacion.modelos.Registro

class HomeAdapter(val clickListener: HomeRegistroListener) :
    ListAdapter<Registro, HomeAdapter.ViewHolder>(HomeDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RegistroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Registro, clickListener: HomeRegistroListener) {
            binding.registro = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
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


class HomeRegistroListener(val clickListener: (id: Int) -> Unit) {
    fun onClick(registro: Registro) = registro.numero?.let { clickListener(it) }
}
