package com.jmat.powertools.base.list.selection

import androidx.recyclerview.selection.SelectionTracker

class MultiSelectPredicate : SelectionTracker.SelectionPredicate<Long?>() {
    override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
        return true
    }

    override fun canSelectMultiple(): Boolean {
        return true
    }

    override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
        return true
    }
}
