package com.glushko.sportcommunity.data.player.network

import com.glushko.sportcommunity.data.admin.schedule.model.Schedule
import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.presentation.player.career.model.CareerWidgetUI
import com.glushko.sportcommunity.presentation.player.model.PlayerActionsInMatchUI
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
        statistics = playerStatistics.mapNotNull { it.toModelUI() },
        currentTeam = playerInfo.currentTeam.toModelUI(),
        playerActions = playerActions.map { it.toModel() }
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
    val amplua: String,
    @SerializedName("current_team")
    val currentTeam: CurrentTeam,
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
    @SerializedName("match")
    val matchInfo: Schedule,
    val actions: PlayerPointsActions
) {
    fun toModel() = PlayerActionsInMatchUI(
        match = matchInfo.toModelResults(),
        actions = actions
    )
}

data class CurrentTeam(
    @SerializedName("team_id")
    val teamId: Int,
    @SerializedName("team_name")
    val teamName: String,
    @SerializedName("team_image")
    val teamImage: String?
) {
    fun toModelUI() = CareerWidgetUI(
        teamId = teamId,
        teamName = teamName,
        teamImage = teamImage ?: ""
    )
}

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