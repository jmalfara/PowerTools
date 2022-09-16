package com.jmat.system.ui.screen

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.system.R
import com.jmat.powertools.R as AppR
import com.jmat.powertools.base.compose.topbar.TitleTopBar
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.system.ui.router.RouterStack
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemIntentScreen(
    routerStack: RouterStack
) {
    var action by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TitleTopBar(
                title = stringResource(id = R.string.system_intent_title),
                onNavigationClick = { routerStack.pop() },
                navigationIconRes = AppR.drawable.ic_arrow_back_24
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.system_intent_description),
                    style = MaterialTheme.typography.bodyLarge
                )
                SelectionContainer {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        text = "android.settings.SETTINGS",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = action,
                    onValueChange = { changeText ->
                        action = changeText
                    },
                    label = { Text(stringResource(id = R.string.system_intent_hint)) }
                )
                Button(
                    modifier = Modifier.padding(vertical = 16.dp),
                    onClick = {
                        val intent = Intent(action.text)
                        context.startActivity(intent)
                    }
                ) {
                    Text(stringResource(id = R.string.system_intent_submit))
                }
            }
        }
    )
}

@Composable
@Preview
fun SystemIntentScreenLight() {
    AppTheme(darkTheme = false) {
        SystemIntentScreen(
            routerStack = RouterStack("", {}, CoroutineScope(EmptyCoroutineContext))
        )
    }
}

@Composable
@Preview
fun SystemIntentScreenDark() {
    AppTheme(darkTheme = true) {
        SystemIntentScreen(
            routerStack = RouterStack("", {}, CoroutineScope(EmptyCoroutineContext))
        )
    }
}