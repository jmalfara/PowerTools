package com.jmat.showcase.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmat.powertools.base.compose.theme.AppTheme
import com.jmat.powertools.base.compose.topbar.TitleTopBar
import com.jmat.powertools.base.textfieldformatting.decimal.formatDecimal
import com.jmat.powertools.base.textfieldformatting.fourdigitcard.formatFourDigitCard
import com.jmat.showcase.R
import java.util.Locale
import com.jmat.powertools.R as AppR

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
    val scrollState = rememberScrollState()

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
                modifier = Modifier
                    .padding(it)
                    .padding(dimensionResource(id = AppR.dimen.layout_padding))
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = stringResource(id = R.string.showcase_text_input_number_formatter),
                    style = MaterialTheme.typography.headlineSmall
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = numberText,
                    onValueChange = { changeText ->
                        numberText = changeText.formatDecimal(Locale.getDefault())
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
                        fourDigitCardText = changeText.formatFourDigitCard()
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

@Preview
@Composable
fun ShowcaseTextInputScreenDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        ShowcaseTextInputScreen(
            onBackPressed = { }
        )
    }
}