package com.glushko.sportcommunity.presentation.tournament.model

data class TournamentInfoDisplayData(
    val isCup: Boolean = false,
    val imageCupGrid: String? = null,
    val tournamentTable: List<TournamentTableDisplayData> = emptyList()
)