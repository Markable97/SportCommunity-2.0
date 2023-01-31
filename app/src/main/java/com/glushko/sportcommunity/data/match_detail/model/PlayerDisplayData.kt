package com.glushko.sportcommunity.data.match_detail.model

import androidx.annotation.DrawableRes
import com.glushko.sportcommunity.R

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
    val ownGoal: Int,
    val timeAction: Int
)

data class PlayerInMatchSegment(
    val segment: MatchSegment,
    val player: PlayerAction? = null,
)

data class PlayerAction(
    val idAction: Int,
    val teamId: Int,
    val playerName: String,
    val assist: String? = null,
    val typeAction: MatchAction,
    val timeAction: Int
)

enum class MatchSegment{
    START, BREAK, END, ACTION_HOME, ACTION_GUEST
}

enum class MatchAction{
    GOAL, PENALTY, PENALTY_OUT, OWN_GOAL, YELLOW, TWO_YELLOW, RED, ASSIST
}
@DrawableRes
fun MatchAction.getDrawable() = when(this){
        MatchAction.GOAL -> R.drawable.goal
        MatchAction.PENALTY -> R.drawable.penalty
        MatchAction.PENALTY_OUT -> R.drawable.penalty_out
        MatchAction.OWN_GOAL -> R.drawable.own_goal
        MatchAction.YELLOW -> R.drawable.yellow_card
        MatchAction.TWO_YELLOW -> R.drawable.red_yellow_card
        MatchAction.RED -> R.drawable.red_card
        MatchAction.ASSIST -> R.drawable.ic_assist
}

