package com.glushko.sportcommunity.data.main_screen.repository

import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.main_screen.leagues.network.toModel
import com.glushko.sportcommunity.data.network.ApiService
import com.glushko.sportcommunity.domain.repository.main_screen.MainRepository
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import it.czerwinski.android.hilt.annotations.BoundTo

@BoundTo(supertype =  MainRepository::class, component = SingletonComponent::class)
class MainRepositoryImpl @Inject constructor(private val api: ApiService): MainRepository {
    override fun getLeagues(): Single<List<LeaguesDisplayData>> {
        return api.getFootballLeagues()
            .subscribeOn(Schedulers.io())
            .map {
                it.toModel()
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
}