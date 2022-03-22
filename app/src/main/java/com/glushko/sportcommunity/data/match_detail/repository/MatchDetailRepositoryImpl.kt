package com.glushko.sportcommunity.data.match_detail.repository

import androidx.annotation.MainThread
import com.glushko.sportcommunity.data.match_detail.model.PlayerDisplayData
import com.glushko.sportcommunity.data.match_detail.network.ResponsePlayersInMatch
import com.glushko.sportcommunity.data.match_detail.network.toModel
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.domain.repository.match_detail.MatchDetailRepository
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype = MatchDetailRepository::class, component = SingletonComponent::class)
class MatchDetailRepositoryImpl @Inject constructor(private val api: ApiService): MatchDetailRepository {
    override fun getMatchDetail(matchId: Long): Single<List<PlayerDisplayData>> {
        return api.getPlayersInMatch(ResponsePlayersInMatch.createMap(matchId))
            .subscribeOn(Schedulers.io())
            .map { it.toModel() }
            .observeOn(AndroidSchedulers.mainThread())
    }
}