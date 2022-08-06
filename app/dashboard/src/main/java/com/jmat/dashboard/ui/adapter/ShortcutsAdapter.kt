package com.jmat.dashboard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmat.dashboard.databinding.LayoutDashboardShortcutBinding
import com.jmat.dashboard.ui.model.ShortcutData
import com.jmat.powertools.base.adapter.GenericDiffer
import com.jmat.powertools.base.extensions.navigateDeeplink

class ShortcutsAdapter : ListAdapter<ShortcutData, ShortcutViewHolder>(
    GenericDiffer()
) {
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortcutViewHolder {
        return ShortcutViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ShortcutViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class ShortcutViewHolder private constructor(
    private val binding: LayoutDashboardShortcutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShortcutData) {
        with(binding) {
            root.setOnClickListener {
                root.context.navigateDeeplink(item.action)
            }
            title.text = item.name

            Glide.with(root)
                .load(item.icon)
                .fitCenter()
                .into(icon)
        }
    }

    companion object {
        operator fun invoke(
            parent: ViewGroup
        ): ShortcutViewHolder {
            return ShortcutViewHolder(
                LayoutDashboardShortcutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}