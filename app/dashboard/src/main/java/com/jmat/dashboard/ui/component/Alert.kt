package com.jmat.dashboard.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun Alert(
    title: String,
    text: String,
    confirmText: String,
    dismissText: String,
    onDismiss: (confirmed: Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss(false)
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss(true)
                }
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss(false)
                }
            ) {
                Text(dismissText)
            }
        }
    )
}