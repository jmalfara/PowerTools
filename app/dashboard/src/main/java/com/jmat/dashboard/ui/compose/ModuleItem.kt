package com.jmat.dashboard.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jmat.dashboard.R
import com.jmat.dashboard.data.model.Module
import com.jmat.powertools.base.compose.theme.AppTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ModuleItem(
    modifier: Modifier = Modifier,
    module: Module,
    installed: Boolean
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .then(modifier)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (icon, name, installStatus) = createRefs()

            GlideImage(
                imageModel = module.iconUrl,
                contentDescription = null,
                previewPlaceholder = R.drawable.ic_baseline_check_circle_24,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .height(Dp(42f))
                    .width(Dp(42f)),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(icon.top)
                        bottom.linkTo(icon.bottom)
                        linkTo(icon.end, installStatus.start, bias = 0f)
                    }
                    .padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = module.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            if (installed) {
                Image(
                    modifier = Modifier.constrainAs(installStatus) {
                        top.linkTo(icon.top)
                        bottom.linkTo(icon.bottom)
                        end.linkTo(parent.end)
                    },
                    painter = painterResource(id = R.drawable.ic_baseline_check_circle_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = module.shortDescription,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@Preview
fun ModuleItemPreview() {
    AppTheme {
        Surface {
            ModuleItem(
                module = Module(
                    name = "Name",
                    author = "Author",
                    iconUrl = "IconUrl",
                    shortDescription = "ShortDescription",
                    installName = "InstallName",
                    entrypoint = "Entrypoint",
                    features = listOf()
                ),
                installed = true
            )
        }
    }
}

@Composable
@Preview
fun ModuleItemDarkPreview() {
    AppTheme(darkTheme = true) {
        Surface {
            Column {
                ModuleItem(
                    module = Module(
                        name = "Name",
                        author = "Author",
                        iconUrl = "IconUrl",
                        shortDescription = "ShortDescription",
                        installName = "InstallName",
                        entrypoint = "Entrypoint",
                        features = listOf()
                    ),
                    installed = true
                )
                ModuleItem(
                    module = Module(
                        name = "Name",
                        author = "Author",
                        iconUrl = "IconUrl",
                        shortDescription = "ShortDescription",
                        installName = "InstallName",
                        entrypoint = "Entrypoint",
                        features = listOf()
                    ),
                    installed = false
                )
            }
        }
    }
}