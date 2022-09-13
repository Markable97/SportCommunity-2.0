package com.glushko.sportcommunity.data.network

import com.glushko.sportcommunity.data.main_screen.leagues.network.ResponseFootballLeagues
import com.glushko.sportcommunity.data.main_screen.model.ResponseMainScreen
import com.glushko.sportcommunity.data.match_detail.network.ResponsePlayersInMatch
import com.glushko.sportcommunity.data.matches.network.ResponseFootballMatches
import com.glushko.sportcommunity.data.squad.network.ResponseFootballSquad
import com.glushko.sportcommunity.data.tournament.network.ResponseTournamentTableFootball
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    companion object{
        //Methods
        const val GET_FOOTBALL_LEAGUES = "GetFootballLeague"
        const val GET_FOOTBALL_DIVISIONS = "GetFootballDivisions"
        const val GET_FOOTBALL_TEAMS = "GetFootballTeams"
        const val GET_TOURNAMENT_TABLE_FOOTBALL = "GetTournamentTableFootball"
        const val GET_FOOTBALL_MATCHES_DIVISION= "GetFootballMatchesDivision"
        const val GET_FOOTBALL_MATCHES_CALENDAR_DIVISION= "GetFootballMatchesCalendarDivision"
        const val GET_FOOTBALL_SQUAD = "GetFootballSquad"
        const val GET_PLAYERS_IN_MATCH = "GetPlayersInMatch"
        const val GET_MAIN_SCREEN = "MainScreen"
        //Param
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

    @FormUrlEncoded
    @POST(GET_MAIN_SCREEN)
    suspend fun getMainScreen(@FieldMap param: Map<String, String>): Response<ResponseMainScreen>

    @GET(GET_FOOTBALL_SQUAD)
    suspend fun getFootballSquad(@Query(PARAM_TEAM_ID) teamId: Int): Response<ResponseFootballSquad>

}