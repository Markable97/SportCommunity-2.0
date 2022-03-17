package com.glushko.sportcommunity.data.division.network

import com.glushko.sportcommunity.data.division.model.FootballDivision
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.network.BaseResponse


data class ResponseFootballDivisions(
    override val success: Int,
    override val message: String,
    val football_divisions: MutableList<FootballDivision> = mutableListOf()
) :
    BaseResponse(success, message) {

    companion object {
        fun createMap(league_id: Int): Map<String, String> {
            val map = HashMap<String, String>()
            map[ApiService.PARAM_FOOTBALL_LEAGUE_ID] = league_id.toString()
            return map
        }
    }
}