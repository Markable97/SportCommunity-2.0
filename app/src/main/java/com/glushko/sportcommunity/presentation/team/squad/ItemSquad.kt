package com.glushko.sportcommunity.presentation.team.squad

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.glushko.sportcommunity.data.squad.model.SquadPlayerUI


class ItemSquadPreview(
    override val values: Sequence<SquadPlayerUI> = listOf(
        SquadPlayerUI(
            playerId = 777,
            playerUrl = "safas",
            amplua = "Полузащитник",
            playerName = "Фамилия Имя Отчество",
            avatarUrl = "asfasfasfas"
        )
    ).asSequence()
 ) : PreviewParameterProvider<SquadPlayerUI>

@Preview
@Composable
fun ItemSquad(
    @PreviewParameter(ItemSquadPreview::class)
    data: SquadPlayerUI,
    onClickItem: ((SquadPlayerUI)-> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
            .clickable { onClickItem?.invoke(data) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(100)
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = data.avatarUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(95.dp)
                    .clip(RoundedCornerShape(100))
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.playerName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.amplua,textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium
            )
        }
    }
}