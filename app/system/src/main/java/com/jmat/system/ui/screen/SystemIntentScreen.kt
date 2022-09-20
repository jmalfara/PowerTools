package com.jmat.system.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.base.compose.topbar.TitleTopBar
import com.jmat.system.R
import com.jmat.system.ui.viewmodel.SystemShortcutsStateHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.jmat.powertools.R as AppR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemIntentScreen(
    navigateBack: () -> Unit = { },
    stateHolder: SystemShortcutsStateHolder
) {
    var action by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current

    val isShortcut = stateHolder.isShortcut.collectAsState(initial = false)

    Scaffold(
        topBar = {
            TitleTopBar(
                title = stringResource(id = R.string.system_intent_title),
                onNavigationClick = { navigateBack() },
                navigationIconRes = AppR.drawable.ic_arrow_back_24,
                actions = {
                    IconButton(
                        onClick = {
                            stateHolder.toggleShortcut()
                        },
                        content = {
                            Icon(
                                painter = painterResource(com.jmat.powertools.R.drawable.ic_star_24),
                                contentDescription = null,
                                tint = if (isShortcut.value) {
                                    Color.Yellow
                                } else LocalContentColor.current
                            )
                        }
                    )
                }
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
                        kotlin.runCatching {
                            val intent = Intent(action.text)
                            context.startActivity(intent)
                        }
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
            navigateBack = { },
            stateHolder = object : SystemShortcutsStateHolder {
                override val isShortcut: Flow<Boolean> = MutableStateFlow(true)
                override fun toggleShortcut() { }
            }
        )
    }
}

@Composable
@Preview
fun SystemIntentScreenDark() {
    AppTheme(darkTheme = true) {
        SystemIntentScreen(
            navigateBack = { },
            stateHolder = object : SystemShortcutsStateHolder {
                override val isShortcut: Flow<Boolean> = MutableStateFlow(true)
                override fun toggleShortcut() { }
            }
        )
    }
}