package com.jmat.powertools.base.list.selection

import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewSelectionTracker<T>(
    selectionId: String,
    val recyclerView: RecyclerView,
    private val selectionAdapter: SelectionAdapter<T>,
    val onStartSelection: (items: () -> List<T>, dismissAction: () -> Unit) -> Unit,
    val onFinishSelection: () -> Unit,
    val onSelectionUpdated: (items: List<T>) -> Unit
) {
    var isSelecting: Boolean = false
    val tracker: SelectionTracker<Long> = SelectionTracker.Builder(
        selectionId,
        recyclerView,
        StableIdKeyProvider(recyclerView),
        ViewModelItemDetailsLookup(recyclerView),
        StorageStrategy.createLongStorage())
        .withSelectionPredicate(MultiSelectPredicate())
        .build()

    init {
        selectionAdapter.selectionTracker = tracker

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onItemStateChanged(key: Long, selected: Boolean) {
                super.onItemStateChanged(key, selected)
                if (tracker.selection.isEmpty) {
                    if (isSelecting) {
                        isSelecting = false
                        onFinishSelection()
                    }
                } else {
                    if (isSelecting.not()) {
                        isSelecting = true
                        onStartSelection(
                            { getSelectedItems() },
                            { tracker.clearSelection() }
                        )
                    }
                }

                onSelectionUpdated(getSelectedItems())
            }
        })
    }

    fun getSelectedItems(): List<T> {
        val items = selectionAdapter.getCurrentList() ?: return listOf()

        return tracker.selection.map { id ->
            items.filterIndexed { index, _ ->
                recyclerView.adapter?.getItemId(index) == id
            }.first()
        }.requireNoNulls()
    }
}