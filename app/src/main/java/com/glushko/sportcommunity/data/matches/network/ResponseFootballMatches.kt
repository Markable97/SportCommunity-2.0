package com.glushko.sportcommunity.data.matches.network

import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.network.BaseResponse
import com.glushko.sportcommunity.data.matches.model.MatchFootball
import com.glushko.sportcommunity.data.matches.model.toModel
import com.glushko.sportcommunity.data.matches.model.toModelCalendar

class ResponseFootballMatches( success: Int,
                               message: String,
                               val matches: MutableList<MatchFootball> = mutableListOf()): BaseResponse(success, message) {
    companion object{
        fun createMap(team_id: Long):Map<String, String>{
            val map = HashMap<String, String>()
            map[ApiService.PARAM_TEAM_ID] = team_id.toString()
            return map
        }
        fun createMap(division_id: Int):Map<String, String>{
            val map = HashMap<String, String>()
            map[ApiService.PARAM_FOOTBALL_DIVISION_ID] = division_id.toString()
            return map
        }
    }
}
fun ResponseFootballMatches.toModel() = matches.map { it.toModel() }
fun ResponseFootballMatches.toModelCalendar() = matches.map { it.toModelCalendar() }