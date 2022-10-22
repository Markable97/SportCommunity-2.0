package com.glushko.sportcommunity.data.admin.schedule.stadium.model

import android.os.Parcelable
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.util.Constants
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
@Parcelize
data class StadiumUI(
    val id: Int,
    val name: String
): Parcelable

fun StadiumUI.toChooseModel(index: Int) = ChooseModel(
    valueType = Constants.TYPE_VALUE_STADIUM,
    valueDisplay = name,
    position = index
)
