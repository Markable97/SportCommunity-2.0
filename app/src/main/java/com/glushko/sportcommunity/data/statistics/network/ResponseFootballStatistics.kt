package com.glushko.sportcommunity.data.statistics.network

import com.glushko.sportcommunity.data.network.BaseResponse
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.data.teams.model.FootballTeam
import com.google.gson.annotations.SerializedName

class ResponseFootballStatistics(success: Int,
                                 message: String,
                                 @SerializedName("players_with_goals")
                                 val playersWithGoals: MutableList<PlayerWithStatistics> = mutableListOf(),
                                 @SerializedName("players_with_assists")
                                 val playersWithAssists: MutableList<PlayerWithStatistics> = mutableListOf(),
                                 @SerializedName("players_with_yellow_cards")
                                 val playersWithYellowCard: MutableList<PlayerWithStatistics> = mutableListOf(),
                                 @SerializedName("players_with_red_cars")
                                 val playersWithRedCards: MutableList<PlayerWithStatistics> = mutableListOf()
): BaseResponse(success, message)

data class PlayersWithStatistics(
    @SerializedName("players_with_goals")
    val playersWithGoals: MutableList<PlayerWithStatistics> = mutableListOf(),
    @SerializedName("players_with_assists")
    val playersWithAssists: MutableList<PlayerWithStatistics> = mutableListOf(),
    @SerializedName("players_with_yellow_cards")
    val playersWithYellowCard: MutableList<PlayerWithStatistics> = mutableListOf(),
    @SerializedName("players_with_red_cars")
    val playersWithRedCards: MutableList<PlayerWithStatistics> = mutableListOf()
)

fun PlayersWithStatistics.toModelTournament(): List<PlayerStatisticAdapter> {
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

data class PlayerWithStatistics(
    @SerializedName("team_id")
    val teamId: Int,
    @SerializedName("team_name")
    val teamName: String,
    @SerializedName("player_id")
    val playerId: Int,
    @SerializedName("player_name")
    val playerName: String,
    @SerializedName("points")
    val points: Int
)

fun PlayerWithStatistics.toModel() = PlayerStatisticDisplayData(
    playerId = playerId,
    playerName = playerName,
    playerTeam = teamName,
    points = points
    //TODO прокинуть url на фотку игрока
)

 fun ResponseFootballStatistics.toModelGoals() = playersWithGoals.map { it.toModel() }
 fun ResponseFootballStatistics.toModelAssists() = playersWithAssists.map { it.toModel() }
 fun ResponseFootballStatistics.toModelYellowCards() = playersWithYellowCard.map { it.toModel() }
 fun ResponseFootballStatistics.toModelRedCards() = playersWithRedCards.map { it.toModel() }
