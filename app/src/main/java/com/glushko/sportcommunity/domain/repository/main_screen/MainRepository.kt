package com.glushko.sportcommunity.domain.repository.main_screen

import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import io.reactivex.Single

interface MainRepository {
    fun getLeagues(): Single<List<LeaguesDisplayData>>
}