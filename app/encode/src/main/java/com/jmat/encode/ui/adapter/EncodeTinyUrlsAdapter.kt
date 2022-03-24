package com.jmat.encode.ui.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.SelectionTracker.SelectionPredicate
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jmat.encode.R
import com.jmat.encode.databinding.LayoutEncodeTinyurlRowBinding
import com.jmat.powertools.TinyUrl
import com.jmat.powertools.base.adapter.GenericDiffer
import com.jmat.powertools.base.list.selection.SelectionAdapter
import com.jmat.powertools.base.list.selection.ViewHolderItemDetails

class EncodeTinyUrlsAdapter : ListAdapter<TinyUrl, TinyUrlViewHolder>(
    GenericDiffer<TinyUrl>()
), SelectionAdapter<TinyUrl> {
    override lateinit var selectionTracker: SelectionTracker<Long>

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TinyUrlViewHolder {
        return TinyUrlViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TinyUrlViewHolder, position: Int) {
        val item = getItem(position)
        val selected = selectionTracker.isSelected(holder.getItemDetails().selectionKey)
        holder.bind(item, selected)
        currentList
    }
}

class TinyUrlViewHolder(
    private val binding: LayoutEncodeTinyurlRowBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderItemDetails<TinyUrl> {
    override val viewHolder: RecyclerView.ViewHolder = this
    override var itemId: Long? = null

    fun bind(item: TinyUrl, selected: Boolean) {
        itemId = item.id.hashCode().toLong()
        binding.originalUrl.text = item.originalUrl
        binding.tinyUrl.text = item.url
        binding.card.isChecked = selected
    }

    companion object {
        operator fun invoke(
            parent: ViewGroup
        ): TinyUrlViewHolder {
            return TinyUrlViewHolder(
                LayoutEncodeTinyurlRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
