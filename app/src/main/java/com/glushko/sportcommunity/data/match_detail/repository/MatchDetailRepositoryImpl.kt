package com.glushko.sportcommunity.data.match_detail.repository

import androidx.annotation.MainThread
import com.glushko.sportcommunity.data.match_detail.model.PlayerDisplayData
import com.glushko.sportcommunity.data.match_detail.network.ResponsePlayersInMatch
import com.glushko.sportcommunity.data.match_detail.network.toModel
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.domain.repository.match_detail.MatchDetailRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype = MatchDetailRepository::class, component = SingletonComponent::class)
class MatchDetailRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils
    ): MatchDetailRepository {
    override suspend fun getMatchDetail(matchId: Long): Resource<List<PlayerDisplayData>> {
        val response = networkUtils.getResponse {
            api.getPlayersInMatch(ResponsePlayersInMatch.createMap(matchId))
        }
        return if (response is Resource.Success){
            Resource.Success(response.data!!.toModel())
        } else {
            Resource.Error(error = response.error)
        }
    }
}