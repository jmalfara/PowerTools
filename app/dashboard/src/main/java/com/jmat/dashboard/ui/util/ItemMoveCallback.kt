package com.jmat.dashboard.ui.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class ItemMoveCallback<T>(
    val saveItemPositions: (List<T>) -> Unit
) : ItemTouchHelper.Callback() {
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: ViewHolder, i: Int) {}
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder:ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onMove(
        recyclerView: RecyclerView, viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        val from = viewHolder.adapterPosition
        val to = target.adapterPosition
        val adapter = recyclerView.adapter as? ListAdapter<T, *>
        val list = adapter?.currentList?.toMutableList() ?: mutableListOf()

        list.removeAt(from).let {
            list.add(to, it)
        }
        adapter?.submitList(list)
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        val adapter = recyclerView.adapter as? ListAdapter<T, *> ?: return
        val list: List<T> = adapter.currentList
        saveItemPositions(list)
    }
}
