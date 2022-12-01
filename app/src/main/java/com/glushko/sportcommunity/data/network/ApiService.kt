package com.glushko.sportcommunity.data.network

import com.glushko.sportcommunity.data.admin.assign_matches.network.RequestMatchScore
import com.glushko.sportcommunity.data.admin.edit_match.network.RequestPlayerInMatchMain
import com.glushko.sportcommunity.data.admin.edit_match.network.RequestPlayerWithAction
import com.glushko.sportcommunity.data.admin.edit_match.network.RequestPlayersWithActionsGoals
import com.glushko.sportcommunity.data.admin.schedule.stadium.network.RequestSchedule
import com.glushko.sportcommunity.data.main_screen.leagues.network.ResponseFootballLeagues
import com.glushko.sportcommunity.data.main_screen.model.ResponseMainScreen
import com.glushko.sportcommunity.data.match_detail.network.ResponsePlayersInMatch
import com.glushko.sportcommunity.data.matches.network.ResponseFootballMatches
import com.glushko.sportcommunity.data.squad.network.ResponseFootballSquad
import com.glushko.sportcommunity.data.statistics.network.ResponseFootballStatistics
import com.glushko.sportcommunity.data.tournament.network.ResponseTournamentTableFootball
import com.google.gson.JsonObject
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
        const val GET_STATISTICS_TEAM = "GetFootballStatisticsTeam"
        const val GET_STATISTICS_TOURNAMENT = "GetFootballStatisticsTournament"
        const val GET_ASSIGN_MATCHES = "GetAssignedMatches"
        const val GET_DIVISIONS = "GetDivisions"
        const val GET_UNASSIGNED_TOURS = "GetUnassignedTours"
        const val GET_UNASSIGNED_MATCHES = "GetUnassignedMatches"
        const val ADD_ASSIGN_MATCHES = "AddAssignMatches"
        const val GET_STADIUMS = "GetStadiums"
        const val ADD_SCHEDULE = "AddSchedule"
        const val GET_SCHEDULE = "GetSchedule"
        const val ADD_MATCH_IN_SCHEDULE = "AddMatchesInSchedule"
        const val GET_ACTIONS = "GetActions"
        const val GET_PLAYERS_FOT_MATCH = "GetPlayersForMatch"
        const val ADD_PLAYERS_FOT_MATCH = "AddPlayerInMatch"
        const val DELETE_PLAYERS_FOT_MATCH = "DeletePlayerInMatch"
        const val ADD_MATCH_SCORE = "AddMatchScore"
        const val ADD_PLAYER_ACTION = "AddPlayerAction"
        const val DELETE_PLAYERS_WITH_ACTION_GOALS = "DeletePlayersWithActionGoals"

        //Param
        const val PARAM_FOOTBALL_LEAGUE_ID = "league_id"
        const val PARAM_FOOTBALL_DIVISION_ID = "division_id"
        const val PARAM_FOOTBALL_SEASON_ID = "season_id"
        const val PARAM_TEAM_ID = "team_id"
        const val PARAM_MATCH_ID = "match_id"
        const val PARAM_TOURNAMENT_ID = "tournament_id"
        const val PARAM_STADIUM_ID = "stadium_id"
        const val PARAM_TOURS = "tours"
        const val PARAM_NATCHES = "matches"
        const val PARAM_ACTION_DELETE = "deleting"

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
    suspend fun getFootballSquad(@Query(PARAM_TEAM_ID) teamId: Int): Response<JsonObject>

    @GET(GET_STATISTICS_TEAM)
    suspend fun getStatisticsTeam(@Query(PARAM_TEAM_ID) teamId: Int): Response<ResponseFootballStatistics>

    @GET(GET_STATISTICS_TOURNAMENT)
    suspend fun getStatisticsTournament(@Query(PARAM_FOOTBALL_DIVISION_ID) divisionId: Int): Response<ResponseFootballStatistics>

    @GET(GET_ASSIGN_MATCHES)
    suspend fun getAssignMatches(
        @Query(PARAM_FOOTBALL_LEAGUE_ID) leagueId: Int,
        @Query("played") played: String? = null
    ): Response<JsonObject>

    @GET(GET_DIVISIONS)
    suspend fun getDivisions(@Query(PARAM_FOOTBALL_LEAGUE_ID) leagueId: Int): Response<JsonObject>

    @GET(GET_UNASSIGNED_TOURS)
    suspend fun getUnassignedTours(@Query(PARAM_TOURNAMENT_ID) tournamentId: Int): Response<JsonObject>

    @GET(GET_UNASSIGNED_MATCHES)
    suspend fun getUnassignedMatches(
        @Query(PARAM_FOOTBALL_LEAGUE_ID) leagueId: Int,
        @Query(PARAM_TOURNAMENT_ID) tournamentId: Int,
        @Query(PARAM_TOURS) tours: String
    ): Response<JsonObject>

    @GET(ADD_ASSIGN_MATCHES)
    suspend fun addAssignMatches(
        @Query(PARAM_NATCHES) matches: String,
        @Query(PARAM_ACTION_DELETE) deleting: Boolean = false
    ): Response<JsonObject>

    @GET(GET_STADIUMS)
    suspend fun getStadiums(
        @Query(PARAM_FOOTBALL_LEAGUE_ID) leagueId: Int
    ): Response<JsonObject>

    @GET(ADD_SCHEDULE)
    suspend fun createSchedule(
        @Query(PARAM_FOOTBALL_LEAGUE_ID) leagueId: Int,
        @Query(PARAM_STADIUM_ID) stadiumId: Int,
        @Query("time_start") timeStart: Long,
        @Query("time_game") timeGame: Int,
        @Query("time_break_between") timeBreakHalf: Int,
        @Query("time_break_after") timeBreakBetween: Int,
        @Query("count_game") countGame: Int
    ): Response<JsonObject>

    @GET(GET_SCHEDULE)
    suspend fun getSchedule(
        @Query(PARAM_FOOTBALL_LEAGUE_ID) leagueId: Int,
        @Query("date") date: Long
    ) : Response<JsonObject>


    @POST(ADD_MATCH_IN_SCHEDULE)
    suspend fun addMatchInSchedule(
        @Body match: RequestSchedule
    ) : Response<JsonObject>

    @GET(GET_ACTIONS)
    suspend fun getActions(): Response<JsonObject>

    @GET(GET_PLAYERS_FOT_MATCH)
    suspend fun getPlayersForMatch(
        @Query("team_home") teamHome: Int,
        @Query("team_guest") teamGuest: Int,
        @Query(PARAM_MATCH_ID) matchId: Long
    ): Response<JsonObject>

    @POST(ADD_PLAYERS_FOT_MATCH)
    suspend fun addPLayersInMatch(
        @Body players: RequestPlayerInMatchMain
    ) : Response<JsonObject>

    @POST(DELETE_PLAYERS_FOT_MATCH)
    suspend fun deletePLayersInMatch(
        @Body players: RequestPlayerInMatchMain
    ) : Response<JsonObject>

    @POST(ADD_MATCH_SCORE)
    suspend fun addScore(
        @Body matchScore: RequestMatchScore
    ): Response<JsonObject>

    @POST(ADD_PLAYER_ACTION)
    suspend fun addPlayerAction(
        @Body playerWithAction: RequestPlayerWithAction
    ) : Response<JsonObject>

    @POST(DELETE_PLAYERS_WITH_ACTION_GOALS)
    suspend fun deletePlayersWithActionsGoals(
        @Body playersWithGoals: RequestPlayersWithActionsGoals
    ) : Response<JsonObject>
}