package com.glushko.sportcommunity.data.tournament.network

import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.data.tournament.helper.TournamentTableHelper
import com.glushko.sportcommunity.data.tournament.model.TournamentTableFootball
import com.glushko.sportcommunity.data.tournament.model.toModel
import com.google.gson.annotations.SerializedName


class ResponseTournamentTableFootball(success: Int = 0,
                                      message: String = "default value",
                                      @SerializedName("tournament_table")var tournamentTable: ResponseTournamentTable
): BaseResponse(success, message) {

    fun toModel() = tournamentTable.table.mapIndexed { position, data ->
        val positionReal = position + 1
        data.toModel(positionReal, TournamentTableHelper.getColorPosition(positionReal, tournamentTable.color))
    }

}