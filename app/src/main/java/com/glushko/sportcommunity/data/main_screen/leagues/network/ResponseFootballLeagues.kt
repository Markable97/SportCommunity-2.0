package com.glushko.sportcommunity.data.main_screen.leagues.network

import com.glushko.sportcommunity.data.main_screen.leagues.model.FootballLeague
import com.glushko.sportcommunity.data.main_screen.leagues.model.toModel
import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.google.gson.annotations.SerializedName


class ResponseFootballLeagues(
    success: Int,
    message: String,
    @SerializedName("football_leagues") val footballLeagues: MutableList<FootballLeague> = mutableListOf()
) : BaseResponse(success, message)

fun ResponseFootballLeagues.toModel() = footballLeagues.map { it.toModel() }
