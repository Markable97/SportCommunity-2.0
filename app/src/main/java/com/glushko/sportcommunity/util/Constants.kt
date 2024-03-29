package com.glushko.sportcommunity.util

import com.glushko.sportcommunity.BuildConfig

object Constants {
    const val BASE_URL = "http://${BuildConfig.HOST_NAME}/SportCommunityServer/"
    const val BASE_URL_IMAGE = "http://${BuildConfig.HOST_NAME}/SportCommunityServer/ImagesOfTeams/"

    const val LFL_URL = "https://lfl.ru/"

    const val DELAY_SPLASH = 3000L

    const val OPEN_FROM_TOURNAMENT = 1
    const val OPEN_FROM_TEAM = 2

    const val TIME_HALF_DEFAULT = 30

    //region shared preferences
    const val SHARED_PREFERENCES = "shared_preferences"

    //Region played matches
    const val TYPE_MATCH_PLAYED = 2
    const val TYPE_MATCH_CALENDAR = 1
    const val TYPE_MATCH_ASSIGN = 0

    //Region type value for ChooseDialog
    const val TYPE_VALUE_DIVISION = 0
    const val TYPE_VALUE_TOUR = 1
    const val TYPE_VALUE_STADIUM = 2
    const val TYPE_VALUE_ACTION = 3
    const val TYPE_VALUE_PLAYER = 4

    //Region type Actions
    const val TYPE_ACTION_GOAL = 1 //id from server DB
    const val TYPE_ACTION_PENALTY = 2 //id from server DB
    const val TYPE_ACTION_PENALTY_OUT = 3
    const val TYPE_ACTION_YELLOW_CARD = 4
    const val TYPE_ACTION_RED_CARD = 5
    const val TYPE_ACTION_OWN_GOAL = 6 //id from server DB
    const val TYPE_ACTION_ASSISTS = 7
    val TYPE_ACTION_GOALS = listOf(
        TYPE_ACTION_GOAL,
        TYPE_ACTION_OWN_GOAL,
        TYPE_ACTION_PENALTY
    )

    const val TYPE_ACTION_ASSIST = 0
}