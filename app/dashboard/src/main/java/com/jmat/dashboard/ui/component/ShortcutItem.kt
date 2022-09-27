package com.jmat.dashboard.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.jmat.dashboard.ui.fixture.DashboardFixtures
import com.jmat.dashboard.ui.model.ShortcutData
import com.jmat.powertools.base.compose.theme.AppTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ShortcutItem(
    modifier: Modifier = Modifier,
    shortcutData: ShortcutData
) {
    Column(
        modifier = Modifier
            .height(height = 128.dp)
            .width(width = 168.dp)
            .padding(8.dp)
            .then(modifier)
    ) {
        ConstraintLayout(
            modifier = Modifier.width(width = 168.dp)
        ) {
            val (icon, name) = createRefs()
            GlideImage(
                imageModel = shortcutData.icon,
                contentDescription = null,
                previewPlaceholder = R.drawable.ic_baseline_check_circle_24,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .height(Dp(42f))
                    .width(Dp(42f)),
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
                style = MaterialTheme.typography.bodyLarge,
                text = shortcutData.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = shortcutData.description,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@Composable
@Preview
fun ShortcutItemPreview() {
    AppTheme {
        Surface {
            ShortcutItem(
                shortcutData = DashboardFixtures.shortcutData
            )
        }
    }
}
