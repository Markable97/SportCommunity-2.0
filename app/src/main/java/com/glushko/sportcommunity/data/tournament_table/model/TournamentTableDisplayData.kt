package com.glushko.sportcommunity.data.tournament_table.model

data class TournamentTableDisplayData(
    val teamId: Int,
    val teamName: String,
    val games: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val scCon: Int,
    val points: Int
)
