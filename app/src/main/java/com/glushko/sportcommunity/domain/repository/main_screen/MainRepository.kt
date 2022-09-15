package com.glushko.sportcommunity.domain.repository.main_screen

import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.main_screen.model.ResponseMainScreen
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.data.statistics.network.PlayersWithStatisticsRes
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.util.Resource

interface MainRepository {

    var tournamentTable: List<TournamentTableDisplayData>
    var calendar: List<MatchFootballDisplayData>
    var results: List<MatchFootballDisplayData>
    var statistics: PlayersWithStatisticsRes

    suspend fun getLeagues(): Resource<List<LeaguesDisplayData>>
    suspend fun getMainScreen(divisionId: Int): Resource<ResponseMainScreen>
}