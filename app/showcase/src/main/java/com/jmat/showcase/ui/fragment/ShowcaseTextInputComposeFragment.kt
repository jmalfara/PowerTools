package com.jmat.showcase.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmat.showcase.ui.compose.theme.AppTheme
import com.jmat.showcase.ui.compose.topbar.TitleTopBar
import com.jmat.showcase.R
import com.jmat.showcase.ui.compose.textfield.formatFourDigitCard
import com.jmat.showcase.ui.compose.textfield.formatNumber
import java.util.*

class ShowcaseTextInputComposeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    ShowcaseTextInputScreen(
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
fun ShowcaseTextInputScreen(
    onBackPressed: () -> Unit
) {
    var numberText by remember { mutableStateOf(TextFieldValue()) }
    var fourDigitCardText by remember { mutableStateOf(TextFieldValue()) }
    Scaffold(
        topBar = {
            TitleTopBar(
                title = stringResource(id = R.string.showcase_action_text_input_compose),
                onNavigationClick = onBackPressed,
                navigationIconRes = com.jmat.powertools.R.drawable.ic_arrow_back_24
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(it)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.showcase_text_input_number_formatter),
                    style = MaterialTheme.typography.headlineSmall
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = numberText,
                    onValueChange = { changeText ->
                        numberText = changeText.formatNumber(Locale.getDefault())
                    },
                    label = { Text("Label") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text(
                    text = stringResource(id = R.string.showcase_text_input_four_digit_card_formatter),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(
                        top = 16.dp
                    )
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = fourDigitCardText,
                    onValueChange = { changeText ->
                        fourDigitCardText = changeText.formatFourDigitCard(fourDigitCardText)
                    },
                    label = { Text("Label") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    )
}

@Preview
@Composable
fun ShowcaseTextInputScreenPreview() {
    AppTheme {
        ShowcaseTextInputScreen(
            onBackPressed = { }
        )
    }
}