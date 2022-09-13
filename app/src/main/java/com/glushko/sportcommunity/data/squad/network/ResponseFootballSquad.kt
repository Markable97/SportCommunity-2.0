package com.glushko.sportcommunity.data.squad.network

import com.glushko.sportcommunity.data.network.BaseResponse
import com.glushko.sportcommunity.data.squad.model.SquadPlayerRes

class ResponseFootballSquad(
    success: Int,
    message: String,
    val players: List<SquadPlayerRes> = listOf()
): BaseResponse(success, message) {
}