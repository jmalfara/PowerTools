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
import com.jmat.system.ui.component.FeatureItem
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.base.compose.topbar.TitleTopBar
import com.jmat.powertools.modules.system.DEEPLINK_SYSTEM_INTENT
import com.jmat.powertools.modules.system.DEEPLINK_SYSTEM_DEEPLINK
import com.jmat.system.R
import com.jmat.system.ui.router.RouterStack
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun SystemLandingScreen(
    routerStack: RouterStack
) {
    Column {
        TitleTopBar(
            title = stringResource(id = com.jmat.powertools.R.string.title_system),
            onNavigationClick = { routerStack.pop() },
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
                    onClick = { routerStack.push(DEEPLINK_SYSTEM_DEEPLINK) }
                )
            }
            item {
                FeatureItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.system_intent_title),
                    onClick = { routerStack.push(DEEPLINK_SYSTEM_INTENT) }
                )
            }
        }
    }
}

@Composable
@Preview
fun SystemLandingScreenPreviewLight() {
    AppTheme(darkTheme = false) {
        SystemLandingScreen(
            routerStack = RouterStack("", {}, CoroutineScope(EmptyCoroutineContext))
        )
    }
}

@Composable
@Preview
fun SystemLandingScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        SystemLandingScreen(
            routerStack = RouterStack("", {}, CoroutineScope(EmptyCoroutineContext))
        )
    }
}