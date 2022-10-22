package com.glushko.sportcommunity.data.admin.assign_matches.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class Match(
    @SerializedName("match_id")
    var matchId: Long,
    @SerializedName("tournament_id")
    var tournamentId: Int,
    @SerializedName("tournament_name")
    var tournamentName: String,
    @SerializedName("tour")
    var tour: String,
    @SerializedName("team_home_id")
    var teamHomeId: Int,
    @SerializedName("team_home_name")
    var teamHomeName: String,
    @SerializedName("team_guest_id")
    var teamGuestId: Int,
    @SerializedName("team_guest_name")
    var teamGuestName: String
)

@Parcelize
data class MatchUI(
    var matchId: Long,
    var tournamentId: Int,
    var tournamentName: String,
    var tour: String,
    var teamHomeId: Int,
    var teamHomeName: String,
    var teamGuestId: Int,
    var teamGuestName: String,
    var isSelect: Boolean = false
): Parcelable

fun Match.toModel() = MatchUI(
    matchId,
    tournamentId,
    tournamentName,
    tour,
    teamHomeId,
    teamHomeName,
    teamGuestId,
    teamGuestName
)