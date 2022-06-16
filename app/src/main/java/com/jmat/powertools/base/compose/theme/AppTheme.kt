package com.jmat.powertools.base.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jmat.powertools.R

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val darkColors = darkColorScheme(
        primary = colorResource(id = R.color.primaryDarkColor),
        onPrimary = colorResource(id = R.color.primaryTextColor),
        secondary = colorResource(id = R.color.secondaryDarkColor),
        onSecondary = colorResource(id = R.color.secondaryTextColor)
    )

    val lightColors = lightColorScheme(
        primary = colorResource(id = R.color.primaryColor),
        onPrimary = colorResource(id = R.color.primaryTextColor),
        secondary = colorResource(id = R.color.secondaryColor),
        onSecondary = colorResource(id = R.color.secondaryTextColor)
    )

    MaterialTheme(
        colorScheme = if (darkTheme) darkColors else lightColors
    ) {
        content()
    }
}

@Preview
@Composable
fun PreviewAppThemeTopBar() {
    Column {
        AppTheme(
            darkTheme = true
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text("TopAppBar")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {  },
                        content = {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back_24),
                                contentDescription = null
                            )
                        }
                    )
                }
            )
        }
        AppTheme(
            darkTheme = false
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text("TopAppBar")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {  },
                        content = {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back_24),
                                contentDescription = null
                            )
                        }
                    )
                }
            )
        }
    }
}

