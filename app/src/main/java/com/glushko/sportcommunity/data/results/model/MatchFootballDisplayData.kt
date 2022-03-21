package com.glushko.sportcommunity.data.results.model

data class MatchFootballDisplayData(
    val divisionName: String,
    val tour: Int,
    val teamHomeName : String,
    val teamHomeGoal: Int,
    val teamGuestName: String,
    val teamGuestGoal: Int
)