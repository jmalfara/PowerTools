package com.jmat.dashboard.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jmat.powertools.base.compose.theme.AppTheme

@Composable
fun DashboardHeader(
    title: String,
    onSearchClicked: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            style = MaterialTheme.typography.headlineLarge,
            text = title,
        )
        if (onSearchClicked != null) {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@Preview
fun DashboardHeaderSearchPreview() {
    AppTheme {
        DashboardHeader(
            title = "Title",
            onSearchClicked = { }
        )
    }
}

@Composable
@Preview
fun DashboardHeaderSearchDarkPreview() {
    AppTheme(darkTheme = true) {
        DashboardHeader(
            title = "Title",
            onSearchClicked = { }
        )
    }
}
