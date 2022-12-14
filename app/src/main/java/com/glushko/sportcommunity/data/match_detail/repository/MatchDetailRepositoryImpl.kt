package com.glushko.sportcommunity.data.match_detail.repository

import com.glushko.sportcommunity.data.admin.edit_match.network.ResponsePlayersForMatch
import com.glushko.sportcommunity.data.match_detail.model.MatchSegment
import com.glushko.sportcommunity.data.match_detail.model.PlayerAction
import com.glushko.sportcommunity.data.match_detail.model.PlayerInMatchSegment
import com.glushko.sportcommunity.data.match_detail.network.ResponsePlayersInMatch
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.domain.repository.match_detail.MatchDetailRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Result
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype = MatchDetailRepository::class, component = SingletonComponent::class)
class MatchDetailRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils
) : MatchDetailRepository {
    override suspend fun getMatchDetail(
        matchId: Long,
        teamHomeId: Int
    ): Result<List<PlayerInMatchSegment>> {
        val response =
            networkUtils.getResponseResult<ResponsePlayersForMatch>(ResponsePlayersForMatch::class.java) {
                api.getPlayersInMatch(ResponsePlayersInMatch.createMap(matchId))
            }
        return when (response) {
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> {
                val actions =
                    response.data.playersWithAction.mapIndexed { index, playerWithAction ->
                        playerWithAction.toModelMatch(index + 1)
                    }
                val actionsInFirstTime = mutableListOf<PlayerAction>()
                val actionsInSecondTime = mutableListOf<PlayerAction>()
                actions.forEach { action ->
                    if (action.timeAction <= 20) { //TODO передавать с сервера
                        actionsInFirstTime.add(action)
                    } else {
                        actionsInSecondTime.add(action)
                    }
                }
                val segments = mutableListOf<PlayerInMatchSegment>()
                segments.add(
                    PlayerInMatchSegment(
                        segment = MatchSegment.START
                    )
                )
                segments.addAll(
                    actionsInFirstTime.sortedBy { it.timeAction  }.map { action ->
                        PlayerInMatchSegment(
                            segment = if (teamHomeId == action.teamId) MatchSegment.ACTION_HOME else MatchSegment.ACTION_GUEST,
                            player = action
                        )
                    }
                )
                segments.add(PlayerInMatchSegment(segment = MatchSegment.BREAK))
                segments.addAll(
                    actionsInSecondTime.map { action ->
                        PlayerInMatchSegment(
                            segment = if (teamHomeId == action.teamId) MatchSegment.ACTION_HOME else MatchSegment.ACTION_GUEST,
                            player = action
                        )
                    }
                )
                segments.add(PlayerInMatchSegment(segment = MatchSegment.END))
                Result.Success(segments)
            }
        }
    }
}