package com.glushko.sportcommunity.data.admin.edit_match.repository

import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.assign_matches.network.ResponseMatches
import com.glushko.sportcommunity.data.admin.assign_matches.network.toModel
import com.glushko.sportcommunity.data.admin.edit_match.model.ActionUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PLayerUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PlayerWithActionUI
import com.glushko.sportcommunity.data.admin.edit_match.network.*
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.data.network.BaseResponse
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
            apiService.getAssignMatches(leagueId, "")
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.toModel())
        }
    }

    override suspend fun getPlayersForMatch(teamHome: Int, teamGuest: Int, matchId: Long): Result<Pair<List<PLayerUI>, List<PlayerWithActionUI>>> {
        val response = networkUtils.getResponseResult<ResponsePlayersForMatch>(ResponsePlayersForMatch::class.java) {
            apiService.getPlayersForMatch(teamHome, teamGuest, matchId)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(
                response.data.players.map { it.toModel() }
                        to response.data.playersWithAction.map { it.toModel() }
            )
        }
    }

    override suspend fun addPlayersInMatch(matchId: Long, players: List<PLayerUI>): Result<String> {
        if (players.isNotEmpty()){
            val response = networkUtils.getResponseResult<BaseResponse>(BaseResponse::class.java){
                apiService.addPLayersInMatch(
                    RequestPlayerInMatchMain(
                        players.map { it.toRequestModel(matchId) }
                    )
                )
            }
            return when(response){
                is Result.Error -> Result.Error(response.exception)
                Result.Loading -> Result.Loading
                is Result.Success -> Result.Success(response.data.message)
            }
        } else {
            return Result.Success("success")
        }
    }

    override suspend fun deletePlayersInMatch(
        matchId: Long,
        players: List<PLayerUI>
    ): Result<String> {
        if (players.isNotEmpty()){
            val response = networkUtils.getResponseResult<BaseResponse>(BaseResponse::class.java){
                apiService.deletePLayersInMatch(
                    RequestPlayerInMatchMain(
                        players.map { it.toRequestModel(matchId) }
                    )
                )
            }
            return when(response){
                is Result.Error -> Result.Error(response.exception)
                Result.Loading -> Result.Loading
                is Result.Success -> Result.Success(response.data.message)
            }
        } else {
            return Result.Success("success")
        }
    }

    override suspend fun addScore(match: MatchUI): Result<String> {
        val response = networkUtils.getResponseResult<BaseResponse>(BaseResponse::class.java){
            apiService.addScore(match.toRequestModel())
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.message)
        }
    }

    override suspend fun addPlayerAction(
        matchId: Long,
        isAdd: Boolean,
        playerWithAction: PlayerWithActionUI
    ): Result<String> {
        val request = playerWithAction.toRequestModel(matchId, isAdd)
            ?: return Result.Error(Exception("Empty"))
        val response = networkUtils.getResponseResult<BaseResponse>(BaseResponse::class.java) {
            apiService.addPlayerAction(request)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.message)
        }
    }

    override suspend fun deletePlayersWithGoals(matchId: Long, playersWithGoals: List<PlayerWithActionUI>): Result<String> {
        val requestDop = playersWithGoals.mapNotNull {
            it.toRequestModel(
                matchId = matchId,
                isAdd = false
            )
        }
        val response = networkUtils.getResponseResult<BaseResponse>(BaseResponse::class.java){
            apiService.deletePlayersWithActionsGoals(RequestPlayersWithActionsGoals(
                players = requestDop )
            )
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.message)
        }
    }
}