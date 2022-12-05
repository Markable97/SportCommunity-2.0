package com.glushko.sportcommunity.data.main_screen.model

import com.glushko.sportcommunity.data.admin.schedule.stadium.model.Schedule
import com.glushko.sportcommunity.data.matches.model.MatchFootball
import com.glushko.sportcommunity.data.matches.model.toModel
import com.glushko.sportcommunity.data.matches.model.toModelCalendar
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.network.BaseResponse
import com.glushko.sportcommunity.data.statistics.network.PlayersWithStatisticsRes
import com.glushko.sportcommunity.data.tournament.model.TournamentTableFootball
import com.glushko.sportcommunity.data.tournament.model.toModel
import com.google.gson.annotations.SerializedName

class ResponseMainScreen(
    success: Int,
    message: String,
    val calendar: List<Schedule> = listOf(),
    val results: List<MatchFootball> = listOf(),
    @SerializedName("tournament_table")
    val tournamentTable: List<TournamentTableFootball> = listOf(),
    val statistics: PlayersWithStatisticsRes
) : BaseResponse(success, message){
    companion object {
        fun createMap(division_id: Int): Map<String, String>{
            val map = HashMap<String, String>()
            map[ApiService.PARAM_FOOTBALL_DIVISION_ID] = division_id.toString()
            return map
        }
    }
}



fun ResponseMainScreen.toCalendar() = calendar.map { it.toModelCalendar() }
fun ResponseMainScreen.toResults() = results.map { it.toModel() }
fun ResponseMainScreen.toTournamentTable() = tournamentTable.mapIndexed { position, data ->
    data.toModel(position + 1)
}