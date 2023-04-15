package com.glushko.sportcommunity.data.squad.model

import com.google.gson.annotations.SerializedName

data class SquadPlayerRes(
    @SerializedName("player_id") val playerId: Int,
    @SerializedName("player_name") val playerName: String,
    @SerializedName("team_name") val teamName: String,
    @SerializedName("team_id") val teamId: Long,
    @SerializedName("player_url") val playerUrl: String,
    val amplua: String,
)

fun SquadPlayerRes.toModel() = SquadPlayerUI(
    playerId = this.playerId,
    playerName = this.playerName,
    amplua = this.amplua,
    playerUrl = this.playerUrl,
    avatarUrl= ""
)

data class SquadPlayerUI(
    val playerId: Int,
    val playerName: String,
    val amplua: String,
    val avatarUrl: String,
    val playerUrl: String
)