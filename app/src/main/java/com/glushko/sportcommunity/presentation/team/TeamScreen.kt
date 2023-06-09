package com.glushko.sportcommunity.presentation.team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.core.ButtonIconMain
import com.glushko.sportcommunity.presentation.core.Widget
import com.glushko.sportcommunity.presentation.statistics.FillingStatisticsWidget
import com.glushko.sportcommunity.presentation.tournament.TournamentFragmentDirections
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.presentation.tournament.tournament_table.ui.CreateTableWidget
import com.glushko.sportcommunity.util.Constants

@Composable
fun TeamScreen(
    teamArg: TeamFragmentArgs,
    navController: NavController,
    tournamentTable: List<TournamentTableDisplayData>,
    statistics: List<PlayerStatisticAdapter>
) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth())  {
        TeamHeader(teamArg = teamArg)
        Spacer(modifier = Modifier.size(10.dp))
        Widget(
            title = "Статистика игроков",
            navigationAction = {
                navController.navigate(
                    TeamFragmentDirections
                        .actionTeamFragmentToStatisticsFragment(
                            teamName = teamArg.teamName,
                            id = teamArg.teamId,
                            openFrom = Constants.OPEN_FROM_TEAM
                        )
                )
            }
        ) {
            FillingStatisticsWidget(statistics) { playerInfo ->
                navController.navigate(
                    TeamFragmentDirections
                        .actionTeamFragmentToPlayerInfoFragment(
                            playerInfo.playerId,
                            playerInfo.playerName,
                            playerInfo.playerUrl
                        )
                )
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Widget(
            title = "Турнирная таблица",
            navigationAction = {
                navController.navigate(TournamentFragmentDirections.actionTournamentFragmentToTournamentTableFragment())
            }
        ) {
            CreateTableWidget(
                response = tournamentTable.take(4),
                positionColor = false
            ) { team -> }
        }
        TeamFooter(navController, teamArg)
    }
}

@Composable
fun TeamHeader(teamArg: TeamFragmentArgs) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(128.dp)
                .padding(20.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(100)
                )
        ) {
            AsyncImage(
                model = teamArg.teamName,
                contentDescription = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = teamArg.teamName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TeamFooter(
    navController: NavController,
    teamArg: TeamFragmentArgs
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)) {
        ButtonIconMain(
            modifier = Modifier.weight(1f),
            textButton = "Состав",
            imageIcon = ImageVector.vectorResource(id = R.drawable.ic_people),
            onClick = {
                navController.navigate(TeamFragmentDirections.actionTeamFragmentToSquadFragment(teamArg.teamId))
            }
        )
        ButtonIconMain(
            modifier = Modifier.weight(1f),
            textButton = "Игры",
            imageIcon = ImageVector.vectorResource(id = R.drawable.ic_ball_soccer_small),
            onClick = {
                navController.navigate(TeamFragmentDirections.actionTeamFragmentToGamesFragment(teamArg.teamId))
            }
        )
    }
    ButtonIconMain(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp),
        textButton = "Медия",
        imageIcon = ImageVector.vectorResource(id = R.drawable.ic_media),
        onClick = {
            navController.navigate(TeamFragmentDirections.actionTeamFragmentToTeamMediaFragment(teamArg.teamId))
        }
    )
}