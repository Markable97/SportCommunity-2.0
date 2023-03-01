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

    fun toModel() = tournamentTable.mapIndexed { position, data ->
        data.toModel(position + 1)
    }

}