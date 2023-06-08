package com.glushko.sportcommunity.presentation.statistics.model

enum class OpenStatisticsFrom {
    OPEN_FROM_TOURNAMENT, OPEN_FROM_TEAM
}

fun Int.getOpenStaticsFrom() : OpenStatisticsFrom {
    return when(this) {
        1 -> OpenStatisticsFrom.OPEN_FROM_TOURNAMENT
        2 -> OpenStatisticsFrom.OPEN_FROM_TEAM
        else -> OpenStatisticsFrom.OPEN_FROM_TOURNAMENT
    }
}