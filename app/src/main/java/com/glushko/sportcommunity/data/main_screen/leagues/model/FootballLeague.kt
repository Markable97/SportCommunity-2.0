package com.glushko.sportcommunity.data.main_screen.leagues.model

import com.glushko.sportcommunity.data.main_screen.division.model.FootballDivision
import com.glushko.sportcommunity.data.main_screen.division.model.toModel
import com.google.gson.annotations.SerializedName

data class FootballLeague(
    @SerializedName("league_id") val leagueId: Int,
    @SerializedName("league_name") val leagueName: String,
    val divisions: List<FootballDivision>
)

fun FootballLeague.toModel() = LeaguesDisplayData(name = leagueName,
id = leagueId,
divisions = divisions.map { it.toModel() })

