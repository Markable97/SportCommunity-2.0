package com.glushko.sportcommunity.data.squad.network

import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.data.statistics.network.PlayerWithStatisticsRes
import com.glushko.sportcommunity.data.statistics.network.PlayersWithStatisticsRes
import com.google.gson.annotations.SerializedName

class ResponseFootballSquad(
    success: Int,
    message: String,
    val squad: List<PlayerWithStatisticsRes> = emptyList(),
    @SerializedName("team_url")
    val teamUrl: String?
    //val players: List<SquadPlayerRes> = listOf()
): BaseResponse(success, message) {
}