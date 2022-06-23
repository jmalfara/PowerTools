package com.jmat.powertools.base.list.actionmode

import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.jmat.powertools.R

class ToolbarActionMode(
    private val menuInflater: MenuInflater,
    private val deleteAction: () -> Unit,
    private val dismissActionMode: () -> Unit
) : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_actionmode, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete -> {
                deleteAction()
                dismissActionMode()
                true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        dismissActionMode()
    }
}