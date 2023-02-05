package com.glushko.sportcommunity.data.main_screen.model

import com.glushko.sportcommunity.data.admin.schedule.model.Schedule
import com.glushko.sportcommunity.data.media.network.MediaRes
import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.data.statistics.network.PlayersWithStatisticsRes
import com.glushko.sportcommunity.data.tournament.model.TournamentTableFootball
import com.glushko.sportcommunity.data.tournament.model.toModel
import com.google.gson.annotations.SerializedName

class ResponseMainScreen(
    success: Int,
    message: String,
    val calendar: List<Schedule> = listOf(),
    val results: List<Schedule> = listOf(),
    @SerializedName("tournament_table")
    val tournamentTable: List<TournamentTableFootball> = listOf(),
    val statistics: PlayersWithStatisticsRes,
    val media: List<MediaRes> = listOf()
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
fun ResponseMainScreen.toResults() = results.map { it.toModelResults() }
fun ResponseMainScreen.toTournamentTable() = tournamentTable.mapIndexed { position, data ->
    data.toModel(position + 1)
}
fun ResponseMainScreen.toMedia() = media.map { it.toModelUI() }