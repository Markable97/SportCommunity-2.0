package com.glushko.sportcommunity.data.admin.schedule.stadium.network

import com.google.gson.annotations.SerializedName

data class RequestSchedule(
    @SerializedName("league_id")
    val leagueId: Int,
    @SerializedName("stadium_id")
    val stadiumId: Int,
    @SerializedName("game_date")
    val gameDate: String,
    @SerializedName("match_id")
    val matchId: Long?,
    @SerializedName("is_deleting")
    val isDeleting: Boolean = false
)
