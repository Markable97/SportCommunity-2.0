package com.glushko.sportcommunity.util

object Constants {
    const val BASE_URL = "http://192.168.0.155:8080/SportCommunityServer/"
    const val BASE_URL_IMAGE = "http://192.168.0.155:8080/SportCommunityServer/ImagesOfTeams/"

    const val OPEN_FROM_TOURNAMENT = 1
    const val OPEN_FROM_TEAM = 2

    //Region type value for ChooseDialog
    const val TYPE_VALUE_DIVISION = 0
    const val TYPE_VALUE_TOUR = 1
    const val TYPE_VALUE_STADIUM = 2
    const val TYPE_VALUE_ACTION = 3

    //Region type Actions
    const val TYPE_ACTION_GOAL = 1 //id from server DB
    const val TYPE_ACTION_PENALTY = 2 //id from server DB
    const val TYPE_ACTION_OWN_GOAL = 6 //id from server DB
    val TYPE_ACTION_GOALS = listOf(
        TYPE_ACTION_GOAL,
        TYPE_ACTION_OWN_GOAL,
        TYPE_ACTION_PENALTY
    )
}