package com.jmat.powertools.base.list.selection

import androidx.recyclerview.selection.SelectionTracker

interface SelectionAdapter<T> {
    var selectionTracker: SelectionTracker<Long>
    fun getCurrentList(): List<T>?
}