package com.glushko.sportcommunity.data.main_screen.division.network

import com.glushko.sportcommunity.data.main_screen.division.model.FootballDivision
import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.data.datasource.network.BaseResponse


class ResponseFootballDivisions(
    success: Int,
    message: String,
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