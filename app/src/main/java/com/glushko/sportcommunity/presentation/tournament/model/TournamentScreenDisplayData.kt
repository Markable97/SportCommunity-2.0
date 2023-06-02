package com.glushko.sportcommunity.presentation.tournament.model

data class TournamentScreenDisplayData(
    val tournamentInfo: TournamentInfoDisplayData,
    val statistics: List<PlayerStatisticAdapter>
) {
    companion object {
        fun empty() = TournamentScreenDisplayData(
            tournamentInfo = TournamentInfoDisplayData(),
            statistics = emptyList()
        )
    }
}
