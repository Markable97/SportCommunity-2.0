package com.glushko.sportcommunity.data.admin.edit_match.network

import com.glushko.sportcommunity.data.admin.edit_match.model.ActionUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PLayerUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PlayerWithActionUI
import com.glushko.sportcommunity.data.network.BaseResponse
import com.google.gson.annotations.SerializedName

class ResponsePlayersForMatch(
    success: Int,
    message: String,
    val players: List<Player> = emptyList(),
    @SerializedName("players_with_action")
    val playersWithAction: List<PlayerWithAction> = emptyList()

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

class PlayerWithAction(
    @SerializedName("match_id")
    var matchId: Long,
    @SerializedName("time")
    var time: String,
    @SerializedName("action_id")
    var actionId: Int,
    @SerializedName("action_name")
    var actionName: String,
    @SerializedName("player_id")
    var playerId: Int,
    @SerializedName("player_name")
    var playerName: String,
    @SerializedName("player_assist_id")
    var playerAssistId: Int,
    @SerializedName("player_assist_name")
    var playerAssistName: String,
    @SerializedName("team_id")
    var teamId: Int,
    @SerializedName("team_name")
    var teamName: String
) {
    fun toModel() = PlayerWithActionUI(
        time = time,
        player = PLayerUI(
            playerId = playerId,
            playerName = playerName,
            teamId = teamId,
            teamName = teamName
        ),
        playerAssist = if(playerAssistId == -1) {
            null
        } else {
            PLayerUI(
                playerId = playerAssistId,
                playerName = playerAssistName,
                teamId = teamId,
                teamName = teamName
            )
        },
        action = ActionUI(
            actionId = actionId,
            actionName = actionName
        )

    )
}



