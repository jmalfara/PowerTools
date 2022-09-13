package com.jmat.dashboard.ui.screen

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jmat.dashboard.R
import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.ui.fixture.DashboardFixtures
import com.jmat.powertools.base.compose.theme.AppTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ModuleDetailsScreen(
    module: Module
) {
    Surface {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            val (icon, name, description) = createRefs()

            GlideImage(
                imageModel = module.iconUrl,
                contentDescription = null,
                previewPlaceholder = R.drawable.ic_baseline_check_circle_24,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .height(Dp(128f))
                    .width(Dp(128f)),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .constrainAs(name) {
                        start.linkTo(icon.end)
                        end.linkTo(parent.end)
                        linkTo(icon.top, icon.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(8.dp),
                style = MaterialTheme.typography.headlineLarge,
                text = module.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                modifier = Modifier
                    .constrainAs(description) {
                        linkTo(parent.start, parent.end, bias = 0f)
                        top.linkTo(icon.bottom)
                    }
                    .padding(top = 8.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                text = module.installName,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview
fun ModuleDetailsScreenDark() {
    AppTheme(darkTheme = true) {
        ModuleDetailsScreen(
            module = DashboardFixtures.module
        )
    }
}

@Composable
@Preview
fun ModuleDetailsScreenLight() {
    AppTheme(darkTheme = false) {
        ModuleDetailsScreen(
            module = DashboardFixtures.module
        )
    }
}