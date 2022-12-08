package com.glushko.sportcommunity.data.matches.model

import android.os.Parcelable
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import kotlinx.parcelize.Parcelize


@Parcelize
data class MatchFootballDisplayData(
    val matchId: Long,
    val leagueName: String,
    val divisionName: String,
    val tour: String,
    val teamHomeId: Int,
    val teamHomeName : String,
    val teamHomeGoal: Int,
    val teamGuestId: Int,
    val teamGuestName: String,
    val teamGuestGoal: Int,
    val played: Int,
    val matchDate: String? = null,
    val stadium: String? = null,
): Parcelable {
    fun toModelEditMatch() = MatchUI(
        matchId = matchId,
        tournamentId = 0,
        tournamentName = divisionName,
        tour = tour,
        teamHomeId = teamHomeId,
        teamHomeName = teamHomeName,
        teamHomeGoals = teamHomeGoal,
        teamGuestId = teamGuestId,
        teamGuestName = teamGuestName,
        teamGuestGoals = teamGuestGoal,
        isSaved = true
    )
}