package com.glushko.sportcommunity.data.match_detail.model

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class Player(
    @SerializedName("player_id") val playerId: Long,
    @SerializedName("player_name") val playerName: String,
    @SerializedName("team_name") val teamName: String,
    @SerializedName("team_id") val teamId: Long,
    val birthday: String?,
    val amplua: String?,
    val number: Int,
    val games: Int,
    val goal: Int,
    val penalty: Int,
    @SerializedName("penalty_out") val penaltyOut: Int,
    val assist: Int,
    val yellow: Int,
    val red: Int,
    @SerializedName("own_goal") val ownGoal: Int,
    @SerializedName("in_game") val inGame: Int
)

fun Player.toModel() = PlayerDisplayData(
    playerName = playerName,
    teamName = teamName,
    teamId = teamId,
    amplua = amplua,
    games = games,
    goal = goal,
    penalty = penalty,
    penaltyOut =penaltyOut,
    assist = assist,
    yellow = yellow,
    red = red,
    ownGoal = ownGoal,
    timeAction = Random.nextInt(1, 60)
)