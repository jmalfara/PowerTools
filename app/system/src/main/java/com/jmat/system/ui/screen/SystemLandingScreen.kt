package com.jmat.system.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.base.compose.topbar.TitleTopBar
import com.jmat.system.R
import com.jmat.system.ui.component.FeatureItem

@Composable
fun SystemLandingScreen(
    navigateClose: () -> Unit = { },
    navigateToDeeplinkScreen: () -> Unit = { },
    navigateToIntentScreen: () -> Unit = { }
) {
    Column {
        TitleTopBar(
            title = stringResource(id = com.jmat.powertools.R.string.title_system),
            onNavigationClick = { navigateClose() },
            navigationIconRes = com.jmat.powertools.R.drawable.ic_close_24
        )
        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 8.dp,
                vertical = 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FeatureItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.system_deeplink_title),
                    onClick = { navigateToDeeplinkScreen() }
                )
            }
            item {
                FeatureItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.system_intent_title),
                    onClick = { navigateToIntentScreen() }
                )
            }
        }
    }
}

@Composable
@Preview
fun SystemLandingScreenPreviewLight() {
    AppTheme(darkTheme = false) {
        SystemLandingScreen()
    }
}

@Composable
@Preview
fun SystemLandingScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        SystemLandingScreen()
    }
}