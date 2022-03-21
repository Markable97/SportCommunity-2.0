package com.glushko.sportcommunity.data.tournament_table.model

import com.google.gson.annotations.SerializedName

data class TournamentTableFootball(
    @SerializedName("id_season") val seasonId:Int,
    @SerializedName("id_league")val  leagueId: Int,
    @SerializedName("league_name")val leagueName: String,
    @SerializedName("id_division")val divisionId: Int,
    @SerializedName("division_name")val divisionName: String,
    @SerializedName("id_team")val teamId: Long,
    @SerializedName("team_name")val teamName: String,
    val games: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    @SerializedName("sc_con")val scCon: Int,
    val points: Int
)

fun TournamentTableFootball.toModel() = TournamentTableDisplayData(
    teamId = teamId.toInt(),
    teamName = teamName,
    games = games,
    wins = wins,
    draws = draws,
    losses = losses,
    scCon = scCon,
    points = points
)