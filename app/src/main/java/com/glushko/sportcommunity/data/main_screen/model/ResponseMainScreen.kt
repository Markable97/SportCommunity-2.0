package com.glushko.sportcommunity.data.main_screen.model

import com.glushko.sportcommunity.data.admin.schedule.model.Schedule
import com.glushko.sportcommunity.data.media.network.MediaRes
import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.data.statistics.network.PlayersWithStatisticsRes
import com.glushko.sportcommunity.data.tournament.helper.TournamentTableHelper
import com.glushko.sportcommunity.data.tournament.model.toModel
import com.glushko.sportcommunity.data.tournament.network.ResponseTournamentTable
import com.glushko.sportcommunity.presentation.tournament.model.TournamentInfoDisplayData
import com.google.gson.annotations.SerializedName

class ResponseMainScreen(
    success: Int,
    message: String,
    val calendar: List<Schedule> = listOf(),
    val results: List<Schedule> = listOf(),
    @SerializedName("tournament_table")
    val tournamentTable: ResponseTournamentTable,
    val statistics: PlayersWithStatisticsRes,
    val media: List<MediaRes> = listOf(),
    @SerializedName("tournament_url")
    val tournamentUrl: String?
) : BaseResponse(success, message){
    companion object {
        fun createMap(division_id: Int): Map<String, String>{
            val map = HashMap<String, String>()
            map[ApiService.PARAM_FOOTBALL_DIVISION_ID] = division_id.toString()
            return map
        }
    }

    fun toTournamentInfo() : TournamentInfoDisplayData {
        val firstItem = tournamentTable.table.firstOrNull()
        val isCup = firstItem?.isCup ?: false
        val imageGrid = firstItem?.imageCupGrid
        return TournamentInfoDisplayData(
            isCup = isCup,
            imageCupGrid = imageGrid,
            tournamentTable = if (isCup) emptyList() else toTournamentTable()
        )
    }

}



fun ResponseMainScreen.toCalendar() = calendar.map { it.toModelCalendar() }
fun ResponseMainScreen.toResults() = results.map { it.toModelResults() }
fun ResponseMainScreen.toTournamentTable() = tournamentTable.table.mapIndexed { position, data ->
    val positionReal = position + 1
    data.toModel(positionReal, TournamentTableHelper.getColorPosition(positionReal, tournamentTable.color))
}
fun ResponseMainScreen.toMedia() = media.map { it.toModelUI() }