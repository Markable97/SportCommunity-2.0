package com.glushko.sportcommunity.data.admin.edit_match.network

import com.glushko.sportcommunity.data.admin.edit_match.model.PLayerUI
import com.glushko.sportcommunity.data.network.BaseResponse
import com.google.gson.annotations.SerializedName

class ResponsePlayersForMatch(
    success: Int,
    message: String,
    val players: List<Player> = emptyList()
): BaseResponse(success, message)

data class Player(
    @SerializedName("team_id")
    val teamId: Int,
    @SerializedName("team_name")
    val teamName: String,
    @SerializedName("player_id")
    val playerId: Int,
    @SerializedName("player_name")
    val playerName: String,
    @SerializedName("in_match")
    val inMatch: Boolean
) {
    fun toModel() = PLayerUI(
        playerId = playerId,
        playerName = playerName,
        teamId = teamId,
        teamName = teamName,
        inMatch = inMatch

    )
}



