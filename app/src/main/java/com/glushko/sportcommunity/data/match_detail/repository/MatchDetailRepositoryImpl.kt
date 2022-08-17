package com.glushko.sportcommunity.data.match_detail.repository

import androidx.annotation.MainThread
import com.glushko.sportcommunity.data.match_detail.model.*
import com.glushko.sportcommunity.data.match_detail.network.ResponsePlayersInMatch
import com.glushko.sportcommunity.data.match_detail.network.toModel
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.domain.repository.match_detail.MatchDetailRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject
import kotlin.random.Random

@BoundTo(supertype = MatchDetailRepository::class, component = SingletonComponent::class)
class MatchDetailRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils
    ): MatchDetailRepository {
    override suspend fun getMatchDetail(matchId: Long): Resource<List<PlayerInMatchSegment>> {
        return Resource.Success(getSamples())
//        val response = networkUtils.getResponse {
//            api.getPlayersInMatch(ResponsePlayersInMatch.createMap(matchId))
//        }
//        return if (response is Resource.Success){
//            Resource.Success(response.data!!.toModel())
//        } else {
//            Resource.Error(error = response.error)
//        }
    }

    private fun getSamples(): List<PlayerInMatchSegment>{
        return listOf(
            PlayerInMatchSegment(MatchSegment.START),
            PlayerInMatchSegment(
                MatchSegment.ACTION_HOME,
                PlayerAction(
                    1, "Глушко Марк", "Глушко Даниил", MatchAction.GOAL, 5
                )
            ),
            PlayerInMatchSegment(
                MatchSegment.ACTION_HOME,
                PlayerAction(
                    2, "Глушко Даниил", null, MatchAction.PENALTY, 12
                )
            ),
            PlayerInMatchSegment(
                MatchSegment.ACTION_GUEST,
                PlayerAction(
                    3, "Глушко Даниил", null, MatchAction.PENALTY_OUT, 12
                )
            ),
            PlayerInMatchSegment(MatchSegment.BREAK),
            PlayerInMatchSegment(
                MatchSegment.ACTION_HOME,
                PlayerAction(
                    4, "Глушко Марк", "null", MatchAction.YELLOW, 37
                )
            ),
            PlayerInMatchSegment(
                MatchSegment.ACTION_HOME,
                PlayerAction(
                    5, "Глушко Марк", "null", MatchAction.TWO_YELLOW, 37
                )
            ),
            PlayerInMatchSegment(
                MatchSegment.ACTION_GUEST,
                PlayerAction(
                    6, "Глушко Марк", "Глушко Даниил", MatchAction.GOAL, 48
                )
            ),
            PlayerInMatchSegment(
                MatchSegment.ACTION_HOME,
                PlayerAction(
                    6, "Глушко Марк", "Глушко Даниил", MatchAction.OWN_GOAL, 48
                )
            ),
            PlayerInMatchSegment(
                MatchSegment.ACTION_GUEST,
                PlayerAction(
                    7, "Глушко Даниил", null, MatchAction.PENALTY, 55
                )
            ),
            PlayerInMatchSegment(
                MatchSegment.ACTION_GUEST,
                PlayerAction(
                    7, "Глушко Даниил", null, MatchAction.RED, 55
                )
            ),
            PlayerInMatchSegment(MatchSegment.END),

        )
    }
}