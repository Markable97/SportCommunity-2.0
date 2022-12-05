package com.glushko.sportcommunity.data.admin.schedule.stadium.model

import com.glushko.sportcommunity.data.admin.assign_matches.model.Match
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.google.gson.annotations.SerializedName

data class Schedule(
    val stadium: Stadium,
    @SerializedName("game_date")
    val gameDate: String,
    val match: Match?
) {
    fun toModelCalendar() = MatchFootballDisplayData(
        matchId = match?.matchId ?: 0,
        leagueName = match?.leagueName ?: "",
        divisionName = match?.tournamentName ?: "",
        tour = match?.tour ?: "",
        teamHomeName = match?.teamHomeName ?: "",
        teamHomeGoal = 0,
        teamGuestName = match?.teamGuestName ?: "",
        teamGuestGoal = 0,
        matchDate = gameDate,
        stadium = stadium.stadiumName,
        played = 0
    )

    fun toModelResults() = MatchFootballDisplayData(
        matchId = match?.matchId ?: 0,
        leagueName = match?.leagueName ?: "",
        divisionName = match?.tournamentName ?: "",
        tour = match?.tour ?: "",
        teamHomeName = match?.teamHomeName ?: "",
        teamHomeGoal = match?.teamHomeGoals?:0,
        teamGuestName = match?.teamGuestName ?: "",
        teamGuestGoal = match?.teamGuestGoals ?: 0,
        matchDate = gameDate,
        stadium = stadium.stadiumName,
        played = 1
    )
}

data class ScheduleUI(
    val stadium: StadiumUI,
    val times: MutableList<TimeScheduleUI>,
    var isOpen: Boolean = false
)