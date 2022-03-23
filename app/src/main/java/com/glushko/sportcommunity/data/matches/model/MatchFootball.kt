package com.glushko.sportcommunity.data.matches.model

import com.glushko.sportcommunity.data.main_screen.division.model.FootballDivision
import com.glushko.sportcommunity.data.teams.model.FootballTeam
import com.google.gson.annotations.SerializedName

data class MatchFootball(
    @SerializedName("season_id") val seasonId: Int,
    @SerializedName("league_name") val leagueName: String,
    @SerializedName("division_info") val divisionInfo : FootballDivision,
    @SerializedName("match_id") val matchId: Long,
    @SerializedName("tour_id") val tourId: Int,
    @SerializedName("team_home") val teamHome: FootballTeam,
    @SerializedName("goal_home") val goalHome:Int,
    @SerializedName("goal_guest") val goalGuest: Int,
    @SerializedName("team_guest") val teamGuest: FootballTeam,
    @SerializedName("match_date") val matchDate: String?,
    @SerializedName("name_stadium") val nameStadium: String?,
    @SerializedName("match_desc") val matchDesc: String?,
    val played: Int
)

fun MatchFootball.toModel() = MatchFootballDisplayData(
    matchId = matchId,
    leagueName = leagueName,
    divisionName = divisionInfo.divisionName,
    tour = tourId,
    teamHomeName = teamHome.team_name,
    teamHomeGoal = goalHome,
    teamGuestName = teamGuest.team_name,
    teamGuestGoal = goalGuest,
    played = played
)
fun MatchFootball.toModelCalendar() = MatchFootballDisplayData(
    matchId = matchId,
    leagueName = leagueName,
    divisionName = divisionInfo.divisionName,
    tour = tourId,
    teamHomeName = teamHome.team_name,
    teamHomeGoal = goalHome,
    teamGuestName = teamGuest.team_name,
    teamGuestGoal = goalGuest,
    played = played,
    matchDate = matchDate,
    stadium = nameStadium
)