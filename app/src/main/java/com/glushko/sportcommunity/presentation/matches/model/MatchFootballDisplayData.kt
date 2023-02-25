package com.glushko.sportcommunity.presentation.matches.model

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
    val teamHomeImage : String? = null,
    val teamHomeGoal: Int,
    val teamGuestId: Int,
    val teamGuestName: String,
    val teamGuestImage: String? = null,
    val teamGuestGoal: Int,
    val played: Int,
    val matchDate: String? = null,
    val stadium: String? = null,
    val screenType: MatchScreenType
): Parcelable {
    fun toModelEditMatch() = MatchUI(
        matchId = matchId,
        tournamentId = 0,
        tournamentName = divisionName,
        tour = tour,
        teamHomeId = teamHomeId,
        teamHomeName = teamHomeName,
        teamHomeImage = teamHomeImage,
        teamHomeGoals = teamHomeGoal,
        teamGuestId = teamGuestId,
        teamGuestName = teamGuestName,
        teamGuestImage = teamGuestImage,
        teamGuestGoals = teamGuestGoal,
        isSaved = true
    )
}