package com.glushko.sportcommunity.presentation.tournament.model

data class TournamentTableDisplayData(
    val position: Int,
    val teamId: Int,
    val teamName: String,
    val teamImage: String?,
    val games: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val scCon: Int,
    val points: Int
)
