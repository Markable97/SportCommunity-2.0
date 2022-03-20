package com.glushko.sportcommunity.data.network

import com.glushko.sportcommunity.data.main_screen.division.network.ResponseFootballDivisions
import com.glushko.sportcommunity.data.main_screen.leagues.network.ResponseFootballLeagues
import com.glushko.sportcommunity.data.teams.network.ResponseFootballTeams
import com.glushko.sportcommunity.data.tournament_table.network.ResponseTournamentTableFootball
import io.reactivex.Observable
import io.reactivex.Single
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

        const val PARAM_FOOTBALL_LEAGUE_ID = "league_id"
        const val PARAM_FOOTBALL_DIVISION_ID = "division_id"
        const val PARAM_FOOTBALL_SEASON_ID = "season_id"
        const val PARAM_TEAM_ID = "team_id"
    }

    @POST(GET_FOOTBALL_LEAGUES)
    fun getFootballLeagues(): Single<ResponseFootballLeagues>

    @FormUrlEncoded
    @POST(GET_FOOTBALL_DIVISIONS)
    fun getFootballDivisions(@FieldMap params: Map<String, String>): Single<ResponseFootballDivisions>

    @FormUrlEncoded
    @POST(GET_FOOTBALL_TEAMS)
    fun getFootballTeams(@FieldMap params: Map<String, String>):Single<ResponseFootballTeams>

    @FormUrlEncoded
    @POST(GET_TOURNAMENT_TABLE_FOOTBALL)
    fun getTournamentTableFootball(@FieldMap params: Map<String, String>): Single<ResponseTournamentTableFootball>

}