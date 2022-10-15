package com.glushko.sportcommunity.data.divisions.model

import com.google.gson.annotations.SerializedName

data class Division(
    @SerializedName("tournamnet_id")
    val tournamentId: Int,
    @SerializedName("division_id")
    val divisionId: Int,
    @SerializedName("division_name")
    val divisionName: String
)

fun Division.toModel() = DivisionUI(
    id = tournamentId,
    name = divisionName
)

data class DivisionUI(
    val id: Int,
    val name: String
)