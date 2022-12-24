package com.glushko.sportcommunity.data.admin.assign_matches.model

import android.os.Parcelable
import com.glushko.sportcommunity.data.admin.assign_matches.network.RequestMatchScore
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class Match(
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("league_name")
    val leagueName: String?,
    @SerializedName("tournament_id")
    val tournamentId: Int,
    @SerializedName("tournament_name")
    val tournamentName: String,
    @SerializedName("tour")
    val tour: String,
    @SerializedName("team_home_id")
    val teamHomeId: Int,
    @SerializedName("team_home_name")
    val teamHomeName: String,
    @SerializedName("team_home_image")
    val teamHomeImage: String?,
    @SerializedName("team_guest_id")
    val teamGuestId: Int,
    @SerializedName("team_guest_name")
    val teamGuestName: String,
    @SerializedName("team_guest_image")
    val teamGuestImage: String?,
    @SerializedName("team_home_goals")
    val teamHomeGoals: Int?,
    @SerializedName("team_guest_goals")
    val teamGuestGoals: Int?,


) {
    fun toModel() = MatchUI(
        matchId,
        tournamentId,
        tournamentName,
        tour,
        teamHomeId,
        teamHomeName,
        teamHomeImage,
        teamGuestId,
        teamGuestName,
        teamGuestImage
    )
}

@Parcelize
data class MatchUI(
    var matchId: Long,
    var tournamentId: Int,
    var tournamentName: String,
    var tour: String,
    var teamHomeId: Int,
    var teamHomeName: String,
    var teamHomeImage: String?,
    var teamGuestId: Int,
    var teamGuestName: String,
    var teamGuestImage: String?,
    var isSelect: Boolean = false,
    var teamHomeGoals: Int? = null,
    var teamGuestGoals: Int? = null,
    var isSaved: Boolean = false
): Parcelable {

    fun toRequestModel() = RequestMatchScore(
        matchId = matchId,
        goalHome = teamHomeGoals,
        goalGuest = teamGuestGoals
    )
}