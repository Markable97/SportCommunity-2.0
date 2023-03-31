package com.glushko.sportcommunity.data.tournament.network

import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.data.tournament.helper.TournamentTableHelper
import com.glushko.sportcommunity.data.tournament.model.TournamentColor
import com.glushko.sportcommunity.data.tournament.model.TournamentTableFootball
import com.glushko.sportcommunity.data.tournament.model.toModel

class ResponseTournamentTable(
    success: Int,
    message: String,
    val table: List<TournamentTableFootball>,
    val color: List<TournamentColor>
) : BaseResponse(success, message) {
    fun toModel() = table.mapIndexed { position, data ->
        val positionReal = position + 1
        data.toModel(positionReal, TournamentTableHelper.getColorPosition(positionReal, color))
    }
}