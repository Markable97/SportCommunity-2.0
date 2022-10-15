package com.glushko.sportcommunity.data.admin.assign_matches.repository

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.assign_matches.network.ResponseMatches
import com.glushko.sportcommunity.data.admin.assign_matches.network.toModel
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.domain.repository.admin.assign_matches.AssignMatchesRepository
import com.glushko.sportcommunity.domain.repository.main_screen.MainRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Result
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype =  AssignMatchesRepository::class, component = SingletonComponent::class)
class AssignMatchesRepositoryImpl @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val api: ApiService
): AssignMatchesRepository {
    override suspend fun getAssignMatches(leagueId: Int): Result<List<MatchUI>> {
        val response = networkUtils.getResponseResult<ResponseMatches>(ResponseMatches::class.java) {
            api.getAssignMatches(leagueId)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.toModel())
        }
    }
}