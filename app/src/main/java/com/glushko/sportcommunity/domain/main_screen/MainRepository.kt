package com.glushko.sportcommunity.domain.main_screen

import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.main_screen.model.ResponseMainScreen
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.data.media.model.ImageUI
import com.glushko.sportcommunity.data.media.model.MediaUI
import com.glushko.sportcommunity.data.statistics.network.PlayersWithStatisticsRes
import com.glushko.sportcommunity.presentation.tournament.model.TournamentInfoDisplayData
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.Result

interface MainRepository {

    var tournamentInfo: TournamentInfoDisplayData
    var calendar: List<MatchFootballDisplayData>
    var results: List<MatchFootballDisplayData>
    var statistics: PlayersWithStatisticsRes
    var media: List<MediaUI>

    suspend fun getLeagues(): Resource<List<LeaguesDisplayData>>
    suspend fun getMainScreen(divisionId: Int): Resource<ResponseMainScreen>
    suspend fun getMatchMedia(matchId: Long): Result<List<ImageUI>>
    fun saveFavoriteTournament(leagueId: Int, division: Int)
    fun deleteFavoriteTournament()
    fun getFavoriteDivision(): Int
}