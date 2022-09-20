package com.jmat.powertools.base.compose.topbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jmat.powertools.R
import com.jmat.powertools.base.compose.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTopBar(
    title: String,
    onNavigationClick: () -> Unit,
    @DrawableRes navigationIconRes: Int,
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationClick,
                content = {
                    Icon(
                        painter = painterResource(navigationIconRes),
                        contentDescription = null
                    )
                }
            )
        },
        actions = actions
    )
}

@Preview
@Composable
fun PreviewTitleBarDark() {
    AppTheme(
        darkTheme = true
    ) {
        Column {
            TitleTopBar(
                title = "Title",
                navigationIconRes = R.drawable.ic_arrow_back_24,
                onNavigationClick = { }
            )
            TitleTopBar(
                title = "Title",
                navigationIconRes = R.drawable.ic_arrow_back_24,
                onNavigationClick = { },
                actions = {
                    IconButton(
                        onClick = { },
                        content = {
                            Icon(
                                painter = painterResource(R.drawable.ic_star_24),
                                contentDescription = null
                            )
                        }
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewTitleBarLight() {
    AppTheme(
        darkTheme = false
    ) {
        TitleTopBar(
            title = "Title",
            navigationIconRes = R.drawable.ic_arrow_back_24,
            onNavigationClick = { }
        )
    }
}
