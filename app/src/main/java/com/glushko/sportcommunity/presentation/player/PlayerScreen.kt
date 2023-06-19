package com.glushko.sportcommunity.presentation.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.glushko.sportcommunity.presentation.player.career.CareerWidget
import com.glushko.sportcommunity.presentation.player.career.model.CareerWidgetUI
import com.glushko.sportcommunity.presentation.player.matches.model.PlayerActionsInMatchUI
import com.glushko.sportcommunity.presentation.player.model.PlayerInfoUI
import com.glushko.sportcommunity.presentation.player.model.PlayerStatisticsUI

@Composable
fun PlayerScreen(
    navController: NavController,
    playerInfo: PlayerInfoUI,
    currentTeam: CareerWidgetUI,
    matches: List<PlayerActionsInMatchUI>,
    statistics: List<PlayerStatisticsUI>
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        PlayerHeader(playerInfo)
        RenderStatisticZone(statistics)
        RenderPlayerCarrierWidget(
            currentTeam = currentTeam,
            navController = navController
        )
        RenderPlayerLastMatchWidget(
            matchesPlayer = matches,
            navController = navController
        )
    }
}


class RenderStatisticZonePreview(
    override val values: Sequence<List<PlayerStatisticsUI>> = listOf(
        PlayerStatisticsUI.getSamples()
    ).asSequence()
) : PreviewParameterProvider<List<PlayerStatisticsUI>>

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun RenderStatisticZone(
    @PreviewParameter(RenderStatisticZonePreview::class)
    statistics: List<PlayerStatisticsUI>
) {
    Column(
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.Center
        ) {
            statistics.forEach {
                ItemPlayerStatistics(it)
            }
        }
    }
}

@Composable
private fun PlayerHeader(
    playerInfo: PlayerInfoUI
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(128.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(100)
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = playerInfo.imageUrl,
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
        }
        Text(text = playerInfo.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = playerInfo.birthday, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = playerInfo.amplua, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun RenderPlayerCarrierWidget(
    currentTeam: CareerWidgetUI,
    navController: NavController
) {
    CareerWidget(
        widgetInfo = currentTeam,
        clickTeamAction = { careerInfo ->
            navController.navigate(
                PlayerInfoFragmentDirections.actionPlayerInfoFragmentToTeamFragment(
                    teamId = careerInfo.teamId,
                    teamName = careerInfo.teamName,
                    teamImage = careerInfo.teamImage
                )
            )
        }
    )
}

@Composable
private fun RenderPlayerLastMatchWidget(
    matchesPlayer: List<PlayerActionsInMatchUI>,
    navController: NavController
) {
    matchesPlayer.firstOrNull()?.let {matchPlayer ->
        Spacer(modifier = Modifier.padding(5.dp))
        LastMatchWidget(
            match = matchPlayer.match,
            navController = navController,
            clickMatchDirection = PlayerInfoFragmentDirections
                .actionPlayerInfoFragmentToDetailMatchFragment(matchPlayer.match),
            playerActions = matchPlayer.actions,
            clickActionTitle = {
                navController
                    .navigate(
                        PlayerInfoFragmentDirections
                            .actionPlayerInfoFragmentToMatchesPlayerFragment(
                                matchesPlayer.toTypedArray()
                            )
                    )
            }
        )
    }
}
class ItemPlayerStatisticsPreview(
    override val values: Sequence<PlayerStatisticsUI> = listOf(PlayerStatisticsUI.getSample()).asSequence()
) : PreviewParameterProvider<PlayerStatisticsUI>
@Preview
@Composable
private fun ItemPlayerStatistics(
    @PreviewParameter(ItemPlayerStatisticsPreview::class)
    data: PlayerStatisticsUI
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color("#569283".toColorInt()),
        modifier = Modifier.padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = 10.dp)
        ){
            Image(
                modifier = Modifier.size(25.dp),
                painter = painterResource(id = data.picture),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = data.actionName),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = data.points.toString(),
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}
