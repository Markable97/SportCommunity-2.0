package com.glushko.sportcommunity.presentation.tournament

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.glushko.sportcommunity.presentation.core.Widget
import com.glushko.sportcommunity.presentation.statistics.FillingStatisticsWidget
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.TournamentInfoDisplayData
import com.glushko.sportcommunity.presentation.tournament.model.TournamentScreenDisplayData
import com.glushko.sportcommunity.presentation.tournament.tournament_table.ui.CreateTable
import com.glushko.sportcommunity.util.Constants

@Composable
fun TournamentScreen(
    navController: NavController,
    tournamentInfo: TournamentScreenDisplayData,
) {
    Column(
    ) {
        RenderTournamentTableWidget(navController = navController, tournamentInfo = tournamentInfo.tournamentInfo)
        RenderStatisticsWidget(navController = navController, statistics = tournamentInfo.statistics, divisionId = tournamentInfo.selectedDivision)
    }
}

@Composable
private fun RenderTournamentTableWidget(
    navController: NavController,
    tournamentInfo: TournamentInfoDisplayData
) {
    if (tournamentInfo.isCup) {
        Widget(
            title = "Сетка турнира",
            navigationAction = {}
        ){}
    } else {
        Widget(
            title = "Турнирная таблица",
            navigationAction = {
                navController.navigate(TournamentFragmentDirections.actionTournamentFragmentToTournamentTableFragment())
            }
        ) {
            CreateTable(
                navController = navController,
                response = tournamentInfo.tournamentTable.take(4),
                positionColor = false
            )
        }
    }
}

@Composable
private fun RenderStatisticsWidget(
    navController: NavController,
    statistics: List<PlayerStatisticAdapter>,
    divisionId: Int
) {
    Widget(
        title = "Статистика игроков",
        navigationAction = {
            navController.navigate(TournamentFragmentDirections.actionTournamentFragmentToStatisticsFragment(
                null,
                Constants.OPEN_FROM_TOURNAMENT,
                divisionId
            ))
        }
    ) {
        FillingStatisticsWidget(statistics) { playerInfo ->
            navController.navigate(
                TournamentFragmentDirections
                    .actionTournamentFragmentToPlayerInfoFragment(
                        playerInfo.playerId,
                        playerInfo.playerName,
                        playerInfo.playerUrl
                    )
            )
        }
    }
}