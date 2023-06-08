package com.glushko.sportcommunity.presentation.tournament.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.glushko.sportcommunity.R
import kotlinx.parcelize.Parcelize

data class PlayerStatisticAdapter(
    val typeStatistics: TypeStatistics,
    val firstPlayer: PlayerStatisticDisplayData?,
    val secondPlayer: PlayerStatisticDisplayData?,
    val thirdPlayer: PlayerStatisticDisplayData?
)

@Parcelize
data class PlayerStatisticDisplayData(
    val playerId: Int,
    val playerName: String,
    val playerTeam: String,
    val points: Int,
    val amplua: String,
    val playerUrl: String,
    val urlAvatar: String? = null
) : Parcelable

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
