package com.glushko.sportcommunity.data.statistics.model

import androidx.annotation.StringRes
import com.glushko.sportcommunity.R

data class PlayerStatisticAdapter(
    val typeStatistics: TypeStatistics,
    val firstPlayer: PlayerStatisticDisplayData?,
    val secondPlayer: PlayerStatisticDisplayData?,
    val thirdPlayer: PlayerStatisticDisplayData?
)

data class PlayerStatisticDisplayData(
    val playerId: Int,
    val playerName: String,
    val playerTeam: String,
    val points: Int,
    val urlAvatar: String? = null
)

enum class TypeStatistics () {
    GOALS, ASSISTS, YELLOW_CARDS, RED_CARDS
}

val TypeStatistics.title
    @StringRes
    get() = when(this){
        TypeStatistics.GOALS -> R.string.statistics_goals
        TypeStatistics.ASSISTS -> R.string.statistics_assist
        TypeStatistics.YELLOW_CARDS -> R.string.statistics_yellow
        TypeStatistics.RED_CARDS -> R.string.statistics_red
    }
