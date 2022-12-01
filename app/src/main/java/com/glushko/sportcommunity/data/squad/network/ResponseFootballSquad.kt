package com.glushko.sportcommunity.data.squad.network

import com.glushko.sportcommunity.data.network.BaseResponse
import com.glushko.sportcommunity.data.statistics.network.PlayerWithStatisticsRes
import com.glushko.sportcommunity.data.statistics.network.PlayersWithStatisticsRes

class ResponseFootballSquad(
    success: Int,
    message: String,
    val squad: List<PlayerWithStatisticsRes> = emptyList()
    //val players: List<SquadPlayerRes> = listOf()
): BaseResponse(success, message) {
}