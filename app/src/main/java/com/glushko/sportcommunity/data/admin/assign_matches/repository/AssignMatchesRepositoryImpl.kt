package com.glushko.sportcommunity.data.admin.assign_matches.repository

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.assign_matches.network.ResponseMatches
import com.glushko.sportcommunity.data.admin.assign_matches.network.toModel
import com.glushko.sportcommunity.data.divisions.model.DivisionUI
import com.glushko.sportcommunity.data.divisions.network.ResponseDivisions
import com.glushko.sportcommunity.data.divisions.network.toModel
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.network.BaseResponse
import com.glushko.sportcommunity.data.tours.ResponseUnassignedTours
import com.glushko.sportcommunity.data.tours.toModel
import com.glushko.sportcommunity.domain.repository.admin.assign_matches.AssignMatchesRepository
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

    override suspend fun getDivisions(leagueId: Int): Result<List<DivisionUI>> {
        val response = networkUtils.getResponseResult<ResponseDivisions>(ResponseDivisions::class.java){
            api.getDivisions(leagueId)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.toModel())
        }
    }

    override suspend fun getUnassignedTours(tournamentId: Int): Result<List<String>> {
        val response = networkUtils.getResponseResult<ResponseUnassignedTours>(ResponseUnassignedTours::class.java) {
            api.getUnassignedTours(tournamentId)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.toModel())
        }
    }

    override suspend fun getUnassignedMatches(
        leagueId: Int,
        tournamentId: Int,
        tours: String
    ): Result<List<MatchUI>> {
        val response = networkUtils.getResponseResult<ResponseMatches>(ResponseMatches::class.java){
            api.getUnassignedMatches(leagueId, tournamentId, tours)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.toModel())
        }
    }

    override suspend fun addAssignMatch(
        matches: List<MatchUI>,
        isDeleting: Boolean
    ): Result<String> {
        val response = networkUtils.getResponseResult<BaseResponse>(BaseResponse::class.java) {
            api.addAssignMatches(
                matches.joinToString(",") {
                    it.matchId.toString()
                }, deleting = isDeleting
            )
        }
        return when (response) {
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.message)
        }
    }


}