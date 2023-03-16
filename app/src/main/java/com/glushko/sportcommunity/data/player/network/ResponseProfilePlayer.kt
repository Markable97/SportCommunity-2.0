package com.glushko.sportcommunity.data.player.network

import com.glushko.sportcommunity.data.admin.schedule.model.Schedule
import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.presentation.player.model.PlayerInfoUI
import com.glushko.sportcommunity.presentation.player.model.PlayerStatisticsUI
import com.glushko.sportcommunity.presentation.player.model.ProfilePlayerUI
import com.google.gson.annotations.SerializedName

class ResponseProfilePlayer(
    success: Int,
    message: String,
    @SerializedName("info")
    val playerInfo: PlayerInfo,
    @SerializedName("player_statistics")
    val playerStatistics: List<PlayerAllStatistics>,
    @SerializedName("player_actions")
    val playerActions: List<PlayerActionsInMatch>
) : BaseResponse(success, message) {
    fun toModelUI() = ProfilePlayerUI(
        info = playerInfo.toModelUI(),
        statistics = playerStatistics.mapNotNull { it.toModelUI() }
        //TODO mapping info from server
    )
}

data class PlayerInfo(
    @SerializedName("player_id")
    val id: Int,
    @SerializedName("player_name")
    val name: String,
    @SerializedName("img_url")
    val imageUrl: String?,
    val birthday: String,
    val amplua: String
) {
    fun toModelUI() = PlayerInfoUI(
        id = id,
        name = name,
        imageUrl = imageUrl ?: "",
        birthday  = birthday,
        amplua = amplua
    )
}

data class PlayerAllStatistics(
    val points: Int,
    @SerializedName("action_id")
    val actionId: Int,
    @SerializedName("action_name")
    val actionName: String
) {
    fun toModelUI() = PlayerStatisticsUI.checkType(actionId, points)
}

data class PlayerActionsInMatch(
    val matchInfo: Schedule,
    val actions: PlayerPointsActions
)

data class PlayerPointsActions(
    @SerializedName("goals")
    val goals: Int,
    @SerializedName("assists")
    val assists: Int,
    @SerializedName("yellow_cards")
    val yellowCards: Int,
    @SerializedName("red_cars")
    val redCars: Int,
    @SerializedName("penalty")
    val penalty: Int,
    @SerializedName("penalty_out")
    val penaltyOut: Int,
    @SerializedName("own_goals")
    val ownGoals: Int
)