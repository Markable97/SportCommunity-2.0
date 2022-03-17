package com.glushko.sportcommunity.data.leagues.network

import com.glushko.sportcommunity.data.leagues.model.FootballLeague
import com.glushko.sportcommunity.data.network.BaseResponse


data class ResponseFootballLeagues(
    override val success: Int,
    override val message: String,
    val football_leagues: MutableList<FootballLeague> = mutableListOf()
) :
    BaseResponse(success, message)