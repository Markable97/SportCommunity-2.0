package com.glushko.sportcommunity.data.teams.network

import com.glushko.sportcommunity.data.teams.model.FootballTeam
import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.data.datasource.network.BaseResponse


class ResponseFootballTeams(success: Int,
                            message: String,
                             val football_teams: MutableList<FootballTeam> = mutableListOf()):
    BaseResponse(success, message) {
    companion object{
        fun createMap(division_id: Int):Map<String, String>{
            val map = HashMap<String, String>()
            map[ApiService.PARAM_FOOTBALL_DIVISION_ID] = division_id.toString()
            return map
        }
    }
}