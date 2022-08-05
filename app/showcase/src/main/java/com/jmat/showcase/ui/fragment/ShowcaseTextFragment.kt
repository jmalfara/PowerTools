package com.jmat.showcase.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.base.compose.topbar.TitleTopBar
import com.jmat.showcase.R
import com.jmat.powertools.R as AppR

class ShowcaseTextFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    ShowcaseTextScreen(
                        onBackPressed = {
                            findNavController().popBackStack()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowcaseTextScreen(
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TitleTopBar(
                title = stringResource(
                    id = R.string.showcase_action_typography
                ),
                onNavigationClick = onBackPressed,
                navigationIconRes = AppR.drawable.ic_arrow_back_24
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.showcase_text_h1
                    ),
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(
                        id = R.string.showcase_text_h2
                    ),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(
                        id = R.string.showcase_text_h3
                    ),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stringResource(
                        id = R.string.showcase_text_h4
                    ),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(
                        id = R.string.showcase_text_h5
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(
                        id = R.string.showcase_text_h6
                    ),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = stringResource(
                        id = R.string.showcase_text_body1
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(
                        id = R.string.showcase_text_body2
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(
                        id = R.string.showcase_text_body2
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(
                        id = R.string.showcase_text_caption
                    ),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewTextDark() {
    AppTheme(darkTheme = true) {
        ShowcaseTextScreen(
            onBackPressed = {}
        )
    }
}

@Preview
@Composable
fun PreviewTextLight() {
    AppTheme(darkTheme = false) {
        ShowcaseTextScreen(
            onBackPressed = {}
        )
    }
}