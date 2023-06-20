package com.glushko.sportcommunity.presentation.match_detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glushko.sportcommunity.data.match_detail.model.MatchAction
import com.glushko.sportcommunity.data.match_detail.model.MatchSegment
import com.glushko.sportcommunity.data.match_detail.model.PlayerAction
import com.glushko.sportcommunity.data.match_detail.model.PlayerInMatchSegment
import com.glushko.sportcommunity.data.match_detail.model.getDrawable

@Preview(showBackground = true)
@Composable
private fun ItemMatchActionHomePreview() {
    ItemMatchAction(
        isHome = true,
        data = PlayerInMatchSegment(
            segment = MatchSegment.ACTION_HOME,
            player = PlayerAction(
                idAction = 1,
                teamId = 1,
                playerName = "ФамилияДлинная ИмяДлинное",
                typeAction = MatchAction.GOAL,
                assist = "Фамилия Имя",
                timeAction = 27
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ItemMatchActionGuestPreview() {
    ItemMatchAction(
        isHome = false,
        data = PlayerInMatchSegment(
            segment = MatchSegment.ACTION_HOME,
            player = PlayerAction(
                idAction = 1,
                teamId = 1,
                playerName = "Фамилия Имя",
                typeAction = MatchAction.RED,
                assist = "ФамилияДлинная ИмяДлинное",
                timeAction = 27
            )
        )
    )
}

@Composable
fun ItemMatchAction(
    isHome: Boolean,
    data: PlayerInMatchSegment
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ItemMatchDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            data.player?.let { playerAction ->
                val modifierRow = Modifier.weight(1F)
                if (isHome) {
                    ItemMatchContentRowHome(playerAction = playerAction, modifierRow = modifierRow)
                } else {
                    ItemMatchContentRowGuest(playerAction = playerAction, modifierRow = modifierRow)
                }
            }
        }
        ItemMatchDivider()
    }
}

@Composable
private fun ItemMatchContentRowHome(playerAction: PlayerAction, modifierRow: Modifier) {
    ItemMatchActionPLayer(player = playerAction, modifier = modifierRow)
    Spacer(modifier = Modifier.size(5.dp))
    ItemMatchActionImage(drawableAction = playerAction.typeAction.getDrawable())
    Spacer(modifier = Modifier.size(5.dp))
    ItemMatchActionTime(time = playerAction.timeAction, modifier = modifierRow, textAlign = TextAlign.Left)
}

@Composable
private fun ItemMatchContentRowGuest(playerAction: PlayerAction, modifierRow: Modifier) {
    ItemMatchActionTime(time = playerAction.timeAction, modifier = modifierRow, textAlign = TextAlign.Right)
    Spacer(modifier = Modifier.size(5.dp))
    ItemMatchActionImage(drawableAction = playerAction.typeAction.getDrawable())
    Spacer(modifier = Modifier.size(5.dp))
    ItemMatchActionPLayer(player = playerAction, modifier = modifierRow)
}

@Composable
private fun ItemMatchActionTime(time: Int, modifier: Modifier, textAlign: TextAlign) {
    Text(
        text = "$time`",
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
private fun ItemMatchActionPLayer(player: PlayerAction, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = player.playerName,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        player.assist?.let { playerAssist ->
            Text(
                text = "($playerAssist)",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
private fun ItemMatchActionImage(@DrawableRes drawableAction: Int) {
    Image(
        painter = painterResource(id = drawableAction),
        contentDescription = "",
        modifier = Modifier.size(48.dp)
    )
}

@Composable
private fun ItemMatchDivider() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
    }
}