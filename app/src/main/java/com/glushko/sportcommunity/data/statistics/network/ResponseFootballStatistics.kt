package com.glushko.sportcommunity.data.statistics.network

import com.glushko.sportcommunity.data.network.BaseResponse
import com.glushko.sportcommunity.data.squad.model.SquadPlayerUI
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.google.gson.annotations.SerializedName

class ResponseFootballStatistics(success: Int,
                                 message: String,
                                 @SerializedName("players_with_goals")
                                 val playersWithGoals: MutableList<PlayerWithStatisticsRes> = mutableListOf(),
                                 @SerializedName("players_with_assists")
                                 val playersWithAssists: MutableList<PlayerWithStatisticsRes> = mutableListOf(),
                                 @SerializedName("players_with_yellow_cards")
                                 val playersWithYellowCard: MutableList<PlayerWithStatisticsRes> = mutableListOf(),
                                 @SerializedName("players_with_red_cars")
                                 val playersWithRedCards: MutableList<PlayerWithStatisticsRes> = mutableListOf()
): BaseResponse(success, message)

data class PlayersWithStatisticsRes(
    @SerializedName("players_with_goals")
    val playersWithGoals: MutableList<PlayerWithStatisticsRes> = mutableListOf(),
    @SerializedName("players_with_assists")
    val playersWithAssists: MutableList<PlayerWithStatisticsRes> = mutableListOf(),
    @SerializedName("players_with_yellow_cards")
    val playersWithYellowCard: MutableList<PlayerWithStatisticsRes> = mutableListOf(),
    @SerializedName("players_with_red_cars")
    val playersWithRedCards: MutableList<PlayerWithStatisticsRes> = mutableListOf()
)

fun PlayersWithStatisticsRes.toModelWidget(): List<PlayerStatisticAdapter> {
    val goals = playersWithGoals.map { it.toModel() }
    val assists = playersWithAssists.map { it.toModel() }
    val yellowCards = playersWithYellowCard.map { it.toModel() }
    val redCards = playersWithRedCards.map { it.toModel() }
    return listOf(
        PlayerStatisticAdapter(
            TypeStatistics.GOALS,
            firstPlayer = goals.getOrNull(0),
            secondPlayer = goals.getOrNull(1),
            thirdPlayer = goals.getOrNull(2)
        ),
        PlayerStatisticAdapter(
            TypeStatistics.ASSISTS,
            firstPlayer = assists.getOrNull(0),
            secondPlayer = assists.getOrNull(1),
            thirdPlayer = assists.getOrNull(2)
        ),
        PlayerStatisticAdapter(
            TypeStatistics.YELLOW_CARDS,
            firstPlayer = yellowCards.getOrNull(0),
            secondPlayer = yellowCards.getOrNull(1),
            thirdPlayer = yellowCards.getOrNull(2)
        ),
        PlayerStatisticAdapter(
            TypeStatistics.RED_CARDS,
            firstPlayer = redCards.getOrNull(0),
            secondPlayer = redCards.getOrNull(1),
            thirdPlayer = redCards.getOrNull(2)
        )
    )
}

fun PlayersWithStatisticsRes.toModelGoals() = playersWithGoals.map { it.toModel() }
fun PlayersWithStatisticsRes.toModelAssists() = playersWithAssists.map { it.toModel() }
fun PlayersWithStatisticsRes.toModelYellowCards() = playersWithYellowCard.map { it.toModel() }
fun PlayersWithStatisticsRes.toModelRedCards() = playersWithRedCards.map { it.toModel() }

fun PlayersWithStatisticsRes.toModelSquad(): List<SquadPlayerUI> {
    val listAll = playersWithGoals.union(playersWithAssists).union(playersWithYellowCard)
        .union(playersWithRedCards)
    return listAll.distinctBy { it.playerId }.sortedBy { it.playerName }.map { it.toModelSquad() }
}
data class PlayerWithStatisticsRes(
    @SerializedName("team_id")
    val teamId: Int,
    @SerializedName("team_name")
    val teamName: String,
    @SerializedName("player_id")
    val playerId: Int,
    @SerializedName("player_name")
    val playerName: String,
    @SerializedName("points")
    val points: Int,
    @SerializedName("amplua")
    val amplua: String,
    @SerializedName("action_id")
    val actionId: Int,
    @SerializedName("action_name")
    val actionName: String,
    @SerializedName("image_url")
    val imageUrl: String?
)

fun PlayerWithStatisticsRes.toModel() = PlayerStatisticDisplayData(
    playerId = playerId,
    playerName = playerName,
    playerTeam = teamName,
    points = points,
    amplua = amplua,
    urlAvatar = imageUrl
)
fun PlayerWithStatisticsRes.toModelSquad() = SquadPlayerUI(
    playerId = playerId,
    playerName = playerName,
    amplua = amplua,
    avatarUrl = imageUrl?:""
)