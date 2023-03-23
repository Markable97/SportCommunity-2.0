package com.glushko.sportcommunity.data.tournament.model

import com.google.gson.annotations.SerializedName

data class TournamentColor(
    val hex: String,
    @SerializedName("tournament_id")
    val tournamentId: Int,
    @SerializedName("start_position")
    val startPosition: Int,
    @SerializedName("end_position")
    val endPosition: Int
)
