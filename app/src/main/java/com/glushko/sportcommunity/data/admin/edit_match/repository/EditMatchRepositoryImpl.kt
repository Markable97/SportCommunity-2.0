package com.glushko.sportcommunity.data.admin.edit_match.repository

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.assign_matches.network.ResponseMatches
import com.glushko.sportcommunity.data.admin.assign_matches.network.toModel
import com.glushko.sportcommunity.data.admin.edit_match.model.ActionUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PLayerUI
import com.glushko.sportcommunity.data.admin.edit_match.network.ResponseActions
import com.glushko.sportcommunity.data.admin.edit_match.network.ResponsePlayersForMatch
import com.glushko.sportcommunity.data.admin.edit_match.network.toModel
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.domain.repository.admin.edit_match.EditMatchRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Result
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype =  EditMatchRepository::class, component = SingletonComponent::class)
class EditMatchRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val networkUtils: NetworkUtils
): EditMatchRepository {
    override suspend fun getActions(): Result<List<ActionUI>> {
        val response = networkUtils.getResponseResult<ResponseActions>(ResponseActions::class.java){
            apiService.getActions()
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.toModel())
        }
    }

    override suspend fun getAssignMatches(leagueId: Int): Result<List<MatchUI>> {
        val response = networkUtils.getResponseResult<ResponseMatches>(ResponseMatches::class.java) {
            apiService.getAssignMatches(leagueId)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.toModel())
        }
    }

    override suspend fun getPlayersForMatch(teamHome: Int, teamGuest: Int): Result<List<PLayerUI>> {
        val response = networkUtils.getResponseResult<ResponsePlayersForMatch>(ResponsePlayersForMatch::class.java) {
            apiService.getPlayersForMatch(teamHome, teamGuest)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.players.map { it.toModel() })
        }
    }
}