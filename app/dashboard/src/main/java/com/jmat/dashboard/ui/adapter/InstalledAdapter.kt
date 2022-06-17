package com.jmat.dashboard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jmat.dashboard.databinding.LayoutDashboardFavouriteBinding
import com.jmat.powertools.Module
import com.jmat.powertools.base.adapter.GenericDiffer
import com.jmat.powertools.base.extensions.navigateDeeplink

class InstalledAdapter : ListAdapter<Module, InstalledViewHolder>(
    GenericDiffer()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstalledViewHolder {
        return InstalledViewHolder(parent)
    }

    override fun onBindViewHolder(holder: InstalledViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class InstalledViewHolder private constructor(
    private val binding: LayoutDashboardFavouriteBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Module) {
        binding.title.text = item.name
        with(binding) {
            root.setOnClickListener {
                root.context.navigateDeeplink(item.entrypoint)
            }
            title.text = item.name
        }
    }

    companion object {
        operator fun invoke(
            parent: ViewGroup
        ): InstalledViewHolder {
            return InstalledViewHolder(
                LayoutDashboardFavouriteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}