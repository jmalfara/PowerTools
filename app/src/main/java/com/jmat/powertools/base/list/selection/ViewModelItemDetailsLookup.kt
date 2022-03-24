package com.jmat.powertools.base.list.selection

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class ViewModelItemDetailsLookup(
    private val recyclerView: RecyclerView
) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view: View? = recyclerView.findChildViewUnder(e.x, e.y)
        return if (view != null) {
            val holder: RecyclerView.ViewHolder = recyclerView.getChildViewHolder(view)
            (holder as? ViewHolderItemDetails<*>)?.getItemDetails()
        } else null
    }
}
