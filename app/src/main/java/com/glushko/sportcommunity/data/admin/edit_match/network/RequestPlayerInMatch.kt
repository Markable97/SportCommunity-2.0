package com.glushko.sportcommunity.data.admin.edit_match.network

import com.google.gson.annotations.SerializedName

data class RequestPlayerInMatchMain(
    val players: List<RequestPlayerInMatch>
)

data class RequestPlayerInMatch(
    @SerializedName("team_id")
    val teamId: Int,
    @SerializedName("team_name")
    val teamName: String,
    @SerializedName("player_id")
    val playerId: Int,
    @SerializedName("player_name")
    val playerName: String,
    @SerializedName("match_id")
    val matchId: Long
)