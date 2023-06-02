package com.glushko.sportcommunity.presentation.player.career

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.core.Widget
import com.glushko.sportcommunity.presentation.player.career.model.CareerWidgetUI

class CareerWidgetPreview : PreviewParameterProvider<CareerWidgetUI> {
    override val values: Sequence<CareerWidgetUI>
        get() = listOf(
          CareerWidgetUI(
              teamName = "Футбольная академия им. Гаврилова",
              teamImage = "",
              teamId = 1
          )
        ).asSequence()
}
@Preview
@Composable
fun CareerWidget(
    @PreviewParameter(CareerWidgetPreview::class) widgetInfo: CareerWidgetUI,
    clickAction: (()->Unit)? = null,
    clickTeamAction: ((CareerWidgetUI)->Unit)? = null,
){
    Widget(
        title = stringResource(id = R.string.player__career),
        backgroundColor = colorResource(id = R.color.primary_color),
        navigationAction = clickAction
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                val modifierText = Modifier.fillMaxWidth()
                Text(
                    modifier = modifierText,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.player__current_club)
                )
                Text(
                    modifier = modifierText,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    text = widgetInfo.teamName
                )
            }
            AsyncImage(
                model = widgetInfo.teamImage,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable(enabled = clickTeamAction != null) {
                        clickTeamAction?.invoke(widgetInfo)
                    }
            )
        }
    }
}
