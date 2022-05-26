package com.jmat.dashboard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.databinding.LayoutDashboardStoreModulePreviewBinding
import com.jmat.powertools.base.adapter.GenericDiffer

class StoreAdapter : ListAdapter<Module, RecyclerView.ViewHolder>(
    GenericDiffer()
) {
    private val singleModuleViewType = 0

    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.previewType) {
            "SINGLE"  -> singleModuleViewType
            else -> singleModuleViewType
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder) {
            is SingleModuleViewHolder -> holder.bind(item as Module)
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
    fun bind(item: Module) {
        with(binding) {
            title.text = item.name
            caption.text = item.author
            description.text = item.shortDescription

            Glide.with(previewImage)
                .load(item.previewUrls.first())
                .fitCenter()
                .into(previewImage)
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