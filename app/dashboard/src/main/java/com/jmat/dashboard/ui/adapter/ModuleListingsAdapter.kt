package com.jmat.dashboard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmat.dashboard.databinding.LayoutDashboardStoreModulePreviewBinding
import com.jmat.dashboard.ui.model.ListingData
import com.jmat.powertools.base.adapter.GenericDiffer

class ModuleListingsAdapter(
    private val onClick: (ListingData) -> Unit
) : ListAdapter<ListingData, RecyclerView.ViewHolder>(
    GenericDiffer()
) {
    private val singleModuleViewType = 0

    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.listing.previewType) {
            "SINGLE"  -> singleModuleViewType
            else -> singleModuleViewType
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder) {
            is SingleModuleViewHolder -> holder.bind(item, onClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            singleModuleViewType -> SingleModuleViewHolder.invoke(parent)
            else -> throw java.lang.RuntimeException("ViewType $viewType not supported")
        }
    }
}

class SingleModuleViewHolder private constructor(
    private val binding: LayoutDashboardStoreModulePreviewBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ListingData, onClick: (ListingData) -> Unit) {
        with(binding) {
            card.setOnClickListener { onClick(item) }
            title.text = item.module.name
            caption.text = item.module.author
            description.text = item.module.shortDescription
            installed.isVisible = item.installed

            Glide.with(previewImage)
                .load(item.listing.previewUrls.first())
                .fitCenter()
                .into(previewImage)

            Glide.with(icon)
                .load(item.module.iconUrl)
                .fitCenter()
                .into(icon)
        }
    }

    companion object {
        operator fun invoke(
            parent: ViewGroup
        ): SingleModuleViewHolder {
            return SingleModuleViewHolder(
                LayoutDashboardStoreModulePreviewBinding
                    .inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
            )
        }
    }
}