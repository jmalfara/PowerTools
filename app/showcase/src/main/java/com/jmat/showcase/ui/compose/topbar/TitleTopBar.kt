package com.jmat.showcase.ui.compose.topbar

import androidx.annotation.DrawableRes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jmat.powertools.R
import com.jmat.showcase.ui.compose.theme.AppTheme

@Composable
fun TitleTopBar(
    title: String,
    onNavigationClick: () -> Unit,
    @DrawableRes navigationIconRes: Int
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
        }
    )
}

@Preview
@Composable
fun PreviewTitleBarDark() {
    AppTheme(
        darkTheme = true
    ) {
        TitleTopBar(
            title = "Title",
            navigationIconRes = R.drawable.ic_arrow_back_24,
            onNavigationClick = { }
        )
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