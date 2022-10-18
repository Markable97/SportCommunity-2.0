package com.glushko.sportcommunity.data.admin.schedule.repository


import com.glushko.sportcommunity.data.admin.schedule.stadium.model.StadiumUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.toModel
import com.glushko.sportcommunity.data.admin.schedule.stadium.network.ResponseStadiums
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.domain.repository.admin.schedule.ScheduleRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Result
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype =  ScheduleRepository::class, component = SingletonComponent::class)
class ScheduleRepositoryImpl @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val api: ApiService
): ScheduleRepository {
    override suspend fun getStadiums(leagueId: Int): Result<List<StadiumUI>> {
        val response = networkUtils.getResponseResult<ResponseStadiums>(ResponseStadiums::class.java){
            api.getStadiums(leagueId)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.stadiums.map { it.toModel() })
        }
    }
}