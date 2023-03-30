package com.glushko.sportcommunity.data.main_screen.repository

import com.glushko.sportcommunity.data.datasource.db.UserStorage
import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.main_screen.leagues.network.toModel
import com.glushko.sportcommunity.data.main_screen.model.*
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.data.media.model.ImageUI
import com.glushko.sportcommunity.data.media.model.MediaUI
import com.glushko.sportcommunity.data.media.network.ImagesResMain
import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.data.statistics.network.PlayersWithStatisticsRes
import com.glushko.sportcommunity.domain.main_screen.MainRepository
import com.glushko.sportcommunity.presentation.tournament.model.TournamentInfoDisplayData
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.Result
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Singleton

@Singleton
@BoundTo(supertype =  MainRepository::class, component = SingletonComponent::class)
class MainRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val networkUtils: NetworkUtils,
    private val userStorage: UserStorage
): MainRepository {

    override var tournamentInfo: TournamentInfoDisplayData = TournamentInfoDisplayData()
    override var calendar: List<MatchFootballDisplayData> = emptyList()
    override var results: List<MatchFootballDisplayData> = emptyList()
    override var statistics: PlayersWithStatisticsRes = PlayersWithStatisticsRes()
    override var media: List<MediaUI> = emptyList()


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

    override suspend fun getMainScreen(divisionId: Int): Resource<ResponseMainScreen> {
        val response = networkUtils.getResponse {
            api.getMainScreen(ResponseMainScreen.createMap(divisionId))
        }
        if (response is Resource.Success){
            tournamentInfo = response.data!!.toTournamentInfo()
            calendar = response.data.toCalendar()
            results = response.data.toResults()
            statistics = response.data.statistics
            media = response.data.toMedia()
        }
        return response
    }

    override suspend fun getMatchMedia(matchId: Long): Result<List<ImageUI>> {
        val response = networkUtils.getResponseResult<ImagesResMain>(ImagesResMain::class.java){
            api.getMediaMatch(matchId)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.images.map { it.toModelUI() })
        }
    }

    override fun saveFavoriteTournament(leagueId: Int, division: Int) {
        userStorage.favoriteLeagueId = leagueId
        userStorage.favoriteDivisionId = division
    }

    override fun deleteFavoriteTournament() {
        userStorage.clearFavorite()
    }

    override fun getFavoriteDivision(): Int {
        return userStorage.favoriteDivisionId
    }
}