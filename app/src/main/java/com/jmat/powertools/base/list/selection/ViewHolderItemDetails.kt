package com.jmat.powertools.base.list.selection

import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

interface ViewHolderItemDetails<T> {
    val viewHolder: RecyclerView.ViewHolder
    var itemId: Long?

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = viewHolder.adapterPosition
            override fun getSelectionKey(): Long? = itemId
        }
}
