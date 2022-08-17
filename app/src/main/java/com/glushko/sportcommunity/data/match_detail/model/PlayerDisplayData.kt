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
    val ownGoal: Int,
    val timeAction: Int
)

data class PlayerInMatchSegment(
    val segment: MatchSegment,
    val player: PlayerAction? = null,
)

data class PlayerAction(
    val idAction: Int,
    val playerName: String,
    val assist: String? = null,
    val typeAction: MatchAction,
    val timeAction: Int
)

enum class MatchSegment{
    START, BREAK, END, ACTION_HOME, ACTION_GUEST
}

enum class MatchAction{
    GOAL, PENALTY, PENALTY_OUT, OWN_GOAL, YELLOW, TWO_YELLOW, RED
}

