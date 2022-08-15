package com.glushko.sportcommunity.domain.repository.main_screen

import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.util.Resource
import io.reactivex.Single

interface MainRepository {
    suspend fun getLeagues(): Resource<List<LeaguesDisplayData>>
}