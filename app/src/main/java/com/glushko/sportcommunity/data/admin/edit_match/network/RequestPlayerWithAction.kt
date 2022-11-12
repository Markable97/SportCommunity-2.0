package com.glushko.sportcommunity.data.admin.edit_match.network

import com.google.gson.annotations.SerializedName

data class RequestPlayerWithAction(
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("player_id")
    val playerId: Int,
    @SerializedName("player_assist_id")
    val playerAssistId: Int,
    @SerializedName("action_id")
    val actionId: Int,
    @SerializedName("is_add")
    val isAdd: Boolean,
    @SerializedName("time")
    val time: String
)
