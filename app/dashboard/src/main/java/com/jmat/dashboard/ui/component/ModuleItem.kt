package com.jmat.dashboard.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.jmat.dashboard.ui.fixture.DashboardFixtures
import com.jmat.dashboard.ui.model.ModuleState
import com.jmat.powertools.base.compose.theme.AppTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ModuleItem(
    modifier: Modifier = Modifier,
    module: Module,
    moduleState: ModuleState
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
            Box(
                modifier = Modifier.constrainAs(installStatus) {
                    top.linkTo(icon.top)
                    end.linkTo(parent.end)
                }
            ) {
                when(moduleState) {
                    ModuleState.Installed -> {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_check_circle_24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                        )
                    }
                    ModuleState.Installing -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                    }
                    ModuleState.Uninstalled -> {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_circle_down_24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                        )
                    }
                    is ModuleState.Failed -> {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_error_outline_24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                        )
                    }
                }
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
                module = DashboardFixtures.module,
                moduleState = ModuleState.Installed
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
                    module = DashboardFixtures.module,
                    moduleState = ModuleState.Installed
                )
                ModuleItem(
                    module = DashboardFixtures.module,
                    moduleState = ModuleState.Uninstalled
                )
                ModuleItem(
                    module = DashboardFixtures.module,
                    moduleState = ModuleState.Installing
                )
                ModuleItem(
                    module = DashboardFixtures.module,
                    moduleState = ModuleState.Failed("SomeError")
                )
            }
        }
    }
}