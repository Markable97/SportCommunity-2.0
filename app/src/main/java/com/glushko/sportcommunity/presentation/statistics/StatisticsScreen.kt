package com.glushko.sportcommunity.presentation.statistics

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.glushko.sportcommunity.presentation.statistics.model.OpenStatisticsFrom
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.presentation.tournament.model.TypeStatistics
import com.glushko.sportcommunity.presentation.tournament.model.title
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StatisticsScreen(
    openFrom: OpenStatisticsFrom,
    navController: NavController,
    playerList: List<PlayerStatisticDisplayData>,
    listState: LazyListState,
    onClickTab: (TypeStatistics) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        val textTitle = when (openFrom) {
            OpenStatisticsFrom.OPEN_FROM_TOURNAMENT -> "Стастистика игроков"
            OpenStatisticsFrom.OPEN_FROM_TEAM -> "Лидеры"
        }
        Text(
            text = textTitle,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        StatisticsTabs(onClickTab = onClickTab, listState)
        StatisticsPlayers(
            listState,
            playerList
        ) { player ->
            navController.navigate(
                StatisticsFragmentDirections.actionStatisticsFragmentToPlayerInfoFragment(player.playerId, player.playerName, player.playerUrl)
            )
        }

    }

}

@Composable
private fun StatisticsTabs(
    onClickTab: (TypeStatistics) -> Unit,
    listState: LazyListState
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        contentColor = Color.Black
    ) {
        TypeStatistics.values().forEachIndexed { index, typeStatistics ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    onClickTab.invoke(typeStatistics)
                    selectedTabIndex = index
                    coroutineScope.launch {
                        delay(600)
                        listState.animateScrollToItem(0)
                    }
                },
                text = {
                    Text(
                        text = stringResource(id = typeStatistics.title),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StatisticsPlayers(
    listState: LazyListState,
    playerList: List<PlayerStatisticDisplayData>,
    onClickPlayer: (PlayerStatisticDisplayData) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items = playerList, key = {it.playerId}) {player ->
            StatisticRow(
                modifier = Modifier.animateItemPlacement(
                    animationSpec = tween(durationMillis = 300)
                ),
                playerInfo = player,
                onClickPlayer = onClickPlayer
            )
        }
    }
}