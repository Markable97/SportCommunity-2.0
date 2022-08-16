package com.glushko.sportcommunity.data.network

import com.glushko.sportcommunity.data.main_screen.division.network.ResponseFootballDivisions
import com.glushko.sportcommunity.data.main_screen.leagues.network.ResponseFootballLeagues
import com.glushko.sportcommunity.data.match_detail.network.ResponsePlayersInMatch
import com.glushko.sportcommunity.data.matches.network.ResponseFootballMatches
import com.glushko.sportcommunity.data.teams.network.ResponseFootballTeams
import com.glushko.sportcommunity.data.tournament_table.network.ResponseTournamentTableFootball
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    companion object{
        //Methods
        const val GET_FOOTBALL_LEAGUES = "GetFootballLeague"
        const val GET_FOOTBALL_DIVISIONS = "GetFootballDivisions"
        const val GET_FOOTBALL_TEAMS = "GetFootballTeams"
        const val GET_TOURNAMENT_TABLE_FOOTBALL = "GetTournamentTableFootball"
        const val GET_FOOTBALL_MATCHES_DIVISION= "GetFootballMatchesDivision"
        const val GET_FOOTBALL_MATCHES_CALENDAR_DIVISION= "GetFootballMatchesCalendarDivision"
        const val GET_PLAYERS_IN_MATCH = "GetPlayersInMatch"

        const val PARAM_FOOTBALL_LEAGUE_ID = "league_id"
        const val PARAM_FOOTBALL_DIVISION_ID = "division_id"
        const val PARAM_FOOTBALL_SEASON_ID = "season_id"
        const val PARAM_TEAM_ID = "team_id"
        const val PARAM_MATCH_ID = "match_id"

    }

    @POST(GET_FOOTBALL_LEAGUES)
    suspend fun getFootballLeagues(): Response<ResponseFootballLeagues>

    @FormUrlEncoded
    @POST(GET_TOURNAMENT_TABLE_FOOTBALL)
    suspend fun getTournamentTableFootball(@FieldMap params: Map<String, String>): Response<ResponseTournamentTableFootball>

    @FormUrlEncoded
    @POST(GET_FOOTBALL_MATCHES_DIVISION)
    suspend fun getFootballMatchesDivision(@FieldMap params: Map<String, String>): Response<ResponseFootballMatches>

    @FormUrlEncoded
    @POST(GET_FOOTBALL_MATCHES_CALENDAR_DIVISION)
    suspend fun getFootballMatchesCalendarDivision(@FieldMap params: Map<String, String>): Response<ResponseFootballMatches>

    @FormUrlEncoded
    @POST(GET_PLAYERS_IN_MATCH)
    suspend fun getPlayersInMatch(@FieldMap param: Map<String, String>): Response<ResponsePlayersInMatch>

}