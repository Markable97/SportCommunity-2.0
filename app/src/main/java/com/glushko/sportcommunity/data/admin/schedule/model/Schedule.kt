package com.glushko.sportcommunity.data.admin.schedule.model

import com.glushko.sportcommunity.data.admin.assign_matches.model.Match
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import com.google.gson.annotations.SerializedName

data class Schedule(
    val stadium: Stadium,
    @SerializedName("game_date")
    val gameDate: String,
    val match: Match?
) {

    fun toModelMatches() = MatchFootballDisplayData(
        matchId = match?.matchId ?: 0,
        leagueName = match?.leagueName ?: "",
        divisionName = match?.tournamentName ?: "",
        tour = match?.tour ?: "",
        teamHomeId = match?.teamHomeId ?: 0,
        teamHomeName = match?.teamHomeName ?: "",
        teamHomeImage = match?.teamHomeImage,
        teamHomeGoal = match?.teamHomeGoals?:0,
        teamGuestId = match?.teamGuestId ?: 0,
        teamGuestName = match?.teamGuestName ?: "",
        teamGuestImage = match?.teamGuestImage,
        teamGuestGoal = match?.teamGuestGoals ?: 0,
        matchDate = gameDate,
        stadium = stadium.stadiumName,
        played = match?.played?:0
    )

    fun toModelCalendar() = MatchFootballDisplayData(
        matchId = match?.matchId ?: 0,
        leagueName = match?.leagueName ?: "",
        divisionName = match?.tournamentName ?: "",
        tour = match?.tour ?: "",
        teamHomeId = match?.teamHomeId ?: 0,
        teamHomeName = match?.teamHomeName ?: "",
        teamHomeImage = match?.teamHomeImage,
        teamHomeGoal = 0,
        teamGuestId = match?.teamGuestId ?: 0,
        teamGuestName = match?.teamGuestName ?: "",
        teamGuestImage = match?.teamGuestImage,
        teamGuestGoal = 0,
        matchDate = gameDate,
        stadium = stadium.stadiumName,
        played = 1
    )

    fun toModelResults() = MatchFootballDisplayData(
        matchId = match?.matchId ?: 0,
        leagueName = match?.leagueName ?: "",
        divisionName = match?.tournamentName ?: "",
        tour = match?.tour ?: "",
        teamHomeId = match?.teamHomeId ?: 0,
        teamHomeName = match?.teamHomeName ?: "",
        teamHomeImage = match?.teamHomeImage,
        teamHomeGoal = match?.teamHomeGoals?:0,
        teamGuestId = match?.teamGuestId ?: -1,
        teamGuestName = match?.teamGuestName ?: "",
        teamGuestImage = match?.teamGuestImage,
        teamGuestGoal = match?.teamGuestGoals ?: 0,
        matchDate = gameDate,
        stadium = stadium.stadiumName,
        played = 2
    )
}