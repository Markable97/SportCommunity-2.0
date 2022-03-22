package com.glushko.sportcommunity.data.match_detail.network


import com.glushko.sportcommunity.data.match_detail.model.Player
import com.glushko.sportcommunity.data.match_detail.model.toModel
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.network.BaseResponse
import com.google.gson.annotations.SerializedName


class ResponsePlayersInMatch(success: Int,
                             message: String,
                             @SerializedName("players_in_match")val playersIinMatch: MutableList<Player> = mutableListOf()): BaseResponse(success, message)  {
    companion object{
        fun createMap(matchId: Long):Map<String, String>{
            val map = HashMap<String, String>()
            map[ApiService.PARAM_MATCH_ID] = matchId.toString()
            return map
        }
    }
}
fun ResponsePlayersInMatch.toModel() = playersIinMatch.map { it.toModel() }