package com.glushko.sportcommunity.presentation.player.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.player.career.model.CareerWidgetUI

data class ProfilePlayerUI(
    val info: PlayerInfoUI,
    val statistics: List<PlayerStatisticsUI>,
    val currentTeam: CareerWidgetUI
)

data class PlayerInfoUI(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val birthday: String,
    val amplua: String
)

enum class PlayerStatisticsUI(
    @StringRes val actionName: Int,
    @DrawableRes val picture: Int,
    var points: Int = 0,
) {
    GAMES(
        actionName = R.string.player_statistics__games,
        picture = R.drawable.ic_trophy
    ),
    GOALS(
        actionName = R.string.player_statistics__goals,
        picture = R.drawable.goal
    ),
    ASSISTS (
        actionName = R.string.player_statistics__assists,
        picture = R.drawable.ic_assist
            ),
    RED_CARDS (
        actionName = R.string.player_statistics__red_cards,
        picture = R.drawable.red_card
            ),
    YELLOW_CARDS(
        actionName = R.string.player_statistics__yellow_cards,
        picture = R.drawable.yellow_card
    ),
    PENALTY (
        actionName = R.string.player_statistics__penalty,
        picture = R.drawable.penalty
            ),
    PENALTY_OUT(
        actionName = R.string.player_statistics__penalty_out,
        picture = R.drawable.penalty_out
    ),
    OWN_GOALS(
        actionName = R.string.player_statistics__own_goals,
        picture = R.drawable.own_goal
    );

    fun setPoint(points: Int) {
        this.points = points
    }

    companion object {
        fun checkType(id: Int, points: Int): PlayerStatisticsUI? {
            return when(id) {
                0 -> GAMES
                1 -> GOALS
                2 -> PENALTY
                3 -> PENALTY_OUT
                4 -> YELLOW_CARDS
                5 -> RED_CARDS
                6 -> OWN_GOALS
                7 -> ASSISTS
                else -> null
            }?.apply { setPoint(points) }
        }
    }
}


