package com.glushko.sportcommunity.data.main_screen.repository

import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.main_screen.leagues.network.toModel
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.domain.repository.main_screen.MainRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import it.czerwinski.android.hilt.annotations.BoundTo
import retrofit2.Response

@BoundTo(supertype =  MainRepository::class, component = SingletonComponent::class)
class MainRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils
    ): MainRepository {
    override suspend fun getLeagues(): Resource<List<LeaguesDisplayData>> {
        val response = networkUtils.getResponse {
            api.getFootballLeagues()
        }
        return if (response is Resource.Success) {
            Resource.Success(response.data!!.toModel())
        } else {
            Resource.Error(error = response.error)
        }
    }
}