package com.jmat.system.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jmat.powertools.base.compose.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = title
        )
    }
}

@Composable
@Preview
fun FeatureItemLight() {
    AppTheme(darkTheme = false) {
        FeatureItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Deeplink Helper",
            onClick = { }
        )
    }
}

@Composable
@Preview
fun FeatureItemDark() {
    AppTheme(darkTheme = true) {
        FeatureItem(
            title = "Deeplink Helper",
            onClick = { }
        )
    }
}