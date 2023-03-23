package com.glushko.sportcommunity.data.tournament.network

import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.data.tournament.model.TournamentColor
import com.glushko.sportcommunity.data.tournament.model.TournamentTableFootball

class ResponseTournamentTable(
    success: Int,
    message: String,
    val table: List<TournamentTableFootball>,
    val color: List<TournamentColor>
) : BaseResponse(success, message)