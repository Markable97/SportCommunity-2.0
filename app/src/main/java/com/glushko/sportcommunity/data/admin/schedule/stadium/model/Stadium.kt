package com.glushko.sportcommunity.data.admin.schedule.stadium.model

import com.glushko.sportcommunity.presentation.admin.schedule.model.StadiumUI
import com.google.gson.annotations.SerializedName

data class Stadium(
    @SerializedName("id")
    val stadiumId: Int,
    @SerializedName("league_id")
    val leagueId: Int,
    @SerializedName("name")
    val stadiumName: String
)

fun Stadium.toModel() = StadiumUI(
    id = stadiumId,
    name = stadiumName
)
