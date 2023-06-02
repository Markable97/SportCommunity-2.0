package com.glushko.sportcommunity.presentation.tournament

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.glushko.sportcommunity.presentation.core.Widget
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.presentation.media.FullPhoto
import com.glushko.sportcommunity.presentation.tournament.model.TournamentInfoDisplayData
import com.glushko.sportcommunity.presentation.tournament.model.TournamentScreenDisplayData
import com.glushko.sportcommunity.presentation.tournament.tournament_table.ui.CreateTable

@Composable
fun TournamentScreen(
    navController: NavController,
    tournamentInfo: TournamentScreenDisplayData
) {
    Column(
    ) {
        RenderTournamentTableWidget(navController, tournamentInfo.tournamentInfo)
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
