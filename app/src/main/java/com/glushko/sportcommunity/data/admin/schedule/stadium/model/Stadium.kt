package com.glushko.sportcommunity.data.admin.schedule.stadium.model

import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.util.Constants
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

data class StadiumUI(
    val id: Int,
    val name: String
)

fun StadiumUI.toChooseModel(index: Int) = ChooseModel(
    valueType = Constants.TYPE_VALUE_STADIUM,
    valueDisplay = name,
    position = index
)
