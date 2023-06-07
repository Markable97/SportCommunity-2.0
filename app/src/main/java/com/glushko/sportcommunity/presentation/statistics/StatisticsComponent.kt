package com.glushko.sportcommunity.presentation.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.presentation.tournament.model.title
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FillingStatisticsWidget(
    statistics: List<PlayerStatisticAdapter>,
) {
    val state = rememberPagerState()
    StatisticsPager(
        pagerState = state,
        statistics = statistics
    )
    Spacer(modifier = Modifier.padding(4.dp))
    StatisticsDotsIndicator(
        totalDots = statistics.size,
        selectedIndex = state.currentPage
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun StatisticsPager(
    pagerState: PagerState,
    statistics: List<PlayerStatisticAdapter>
) {
    HorizontalPager(count = statistics.size, state = pagerState) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = statistics[pagerState.currentPage].typeStatistics.title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            val statisticsInfo = statistics[pagerState.currentPage]
            StatisticRow(playerInfo = statisticsInfo.firstPlayer)
            StatisticRow(playerInfo = statisticsInfo.secondPlayer)
            StatisticRow(playerInfo = statisticsInfo.thirdPlayer)
        }
    }
}

@Composable
private fun StatisticsDotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color = Color("#497568".toColorInt()))
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(color = Color("#121212".toColorInt()))
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun StatisticRow(playerInfo: PlayerStatisticDisplayData?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
    ) {
        StatisticsFillingRow(playerInfo)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = (48 + 5).dp)
                .background(Color("#121212".toColorInt()))
        )
    }
}

@Composable
private fun StatisticsFillingRow(playerInfo: PlayerStatisticDisplayData?) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(
            model = playerInfo?.playerUrl,
            error = painterResource(R.drawable.ic_healing_black_36dp),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .height(48.dp)
                .width(48.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color("#C195C9BF".toColorInt()))
        ) 
        Spacer(modifier = Modifier.padding(start = 5.dp))
        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = playerInfo?.playerName ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(text = playerInfo?.playerTeam ?: "")
        }
        Text(
            text = playerInfo?.points?.toString() ?: "",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
