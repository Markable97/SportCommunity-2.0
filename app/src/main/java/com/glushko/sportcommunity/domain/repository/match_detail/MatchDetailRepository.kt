package com.glushko.sportcommunity.domain.repository.match_detail

import com.glushko.sportcommunity.data.match_detail.model.PlayerDisplayData
import io.reactivex.Single

interface MatchDetailRepository {
    fun getMatchDetail(matchId: Long): Single<List<PlayerDisplayData>>
}