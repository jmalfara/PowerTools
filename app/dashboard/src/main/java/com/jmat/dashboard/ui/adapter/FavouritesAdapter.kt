package com.jmat.dashboard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmat.dashboard.databinding.LayoutDashboardFavouriteBinding
import com.jmat.powertools.base.adapter.GenericDiffer
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.data.model.Feature

class FavouritesAdapter : ListAdapter<Feature, FeatureViewHolder>(
    GenericDiffer()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        return FeatureViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class FeatureViewHolder private constructor(
    private val binding: LayoutDashboardFavouriteBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Feature) {
        with(binding) {
            root.setOnClickListener {
                root.context.navigateDeeplink(item.entrypoint)
            }
            title.text = item.title
            description.text = item.description
            module.text = item.module

            Glide.with(root)
                .load(item.iconUrl)
                .fitCenter()
                .into(icon)
        }
    }

    companion object {
        operator fun invoke(
            parent: ViewGroup
        ): FeatureViewHolder {
            return FeatureViewHolder(
                LayoutDashboardFavouriteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}