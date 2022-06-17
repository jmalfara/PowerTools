package com.jmat.dashboard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jmat.dashboard.databinding.LayoutDashboardFavouriteBinding
import com.jmat.powertools.Favourite
import com.jmat.powertools.base.adapter.GenericDiffer
import com.jmat.powertools.base.extensions.navigateDeeplink

class FavouritesAdapter : ListAdapter<Favourite, FavouritesViewHolder>(
    GenericDiffer()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        return FavouritesViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class FavouritesViewHolder private constructor(
    private val binding: LayoutDashboardFavouriteBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Favourite) {
        binding.title.text = item.deeplink
        with(binding) {
            root.setOnClickListener {
                root.context.navigateDeeplink(item.deeplink)
            }
            title.text = item.deeplink
        }
    }

    companion object {
        operator fun invoke(
            parent: ViewGroup
        ): FavouritesViewHolder {
            return FavouritesViewHolder(
                LayoutDashboardFavouriteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}