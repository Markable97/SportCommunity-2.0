package com.glushko.sportcommunity.presentation.team.model

import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.TournamentTableDisplayData

data class TeamDisplayData(
    val tournamentTable: List<TournamentTableDisplayData>,
    val statistics: List<PlayerStatisticAdapter>
)