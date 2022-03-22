package com.glushko.sportcommunity.data.match_detail.model

data class PlayerDisplayData(
    val playerName: String,
    val teamName: String,
    val teamId: Long,
    val amplua: String?,
    val games: Int,
    val goal: Int,
    val penalty: Int,
    val penaltyOut: Int,
    val assist: Int,
    val yellow: Int,
    val red: Int,
    val ownGoal: Int
)
