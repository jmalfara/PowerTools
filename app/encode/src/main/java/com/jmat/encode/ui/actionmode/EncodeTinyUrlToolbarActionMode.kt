package com.jmat.encode.ui.actionmode

import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

class EncodeTinyUrlToolbarActionMode(
    private val menuInflater: MenuInflater,
    private val deleteAction: () -> Unit,
    private val dismissActionMode: () -> Unit
) : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        menuInflater.inflate(com.jmat.powertools.R.menu.menu_delete_actionmode, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return when (item?.itemId) {
            com.jmat.powertools.R.id.delete -> {
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