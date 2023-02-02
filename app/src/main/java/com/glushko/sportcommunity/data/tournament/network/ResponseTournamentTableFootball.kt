package com.glushko.sportcommunity.data.tournament.network

import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.data.tournament.model.TournamentTableFootball
import com.glushko.sportcommunity.data.tournament.model.toModel
import com.google.gson.annotations.SerializedName


class ResponseTournamentTableFootball(success: Int = 0,
                                      message: String = "default value",
                                      @SerializedName("tournament_table")var tournamentTable: List<TournamentTableFootball> = listOf()
): BaseResponse(success, message) {
    companion object {
        fun createMap(division_id: Int, season_id: Int, team_id: Long): Map<String, String>{
            val map = HashMap<String, String>()
            map[ApiService.PARAM_FOOTBALL_DIVISION_ID] = division_id.toString()
            map[ApiService.PARAM_FOOTBALL_SEASON_ID] = season_id.toString()
            map[ApiService.PARAM_TEAM_ID] = team_id.toString()
            return map
        }
    }
}

fun ResponseTournamentTableFootball.toModel() = tournamentTable.mapIndexed { position, data ->
    data.toModel(position + 1)
}