package com.glushko.sportcommunity.data.network

import com.glushko.sportcommunity.data.division.network.ResponseFootballDivisions
import com.glushko.sportcommunity.data.leagues.network.ResponseFootballLeagues
import com.glushko.sportcommunity.data.teams.network.ResponseFootballTeams
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

        const val PARAM_FOOTBALL_LEAGUE_ID = "league_id"
        const val PARAM_FOOTBALL_DIVISION_ID = "division_id"
    }

    @POST(GET_FOOTBALL_LEAGUES)
    fun getFootballLeagues(): Single<ResponseFootballLeagues>

    @FormUrlEncoded
    @POST(GET_FOOTBALL_DIVISIONS)
    fun getFootballDivisions(@FieldMap params: Map<String, String>): Single<ResponseFootballDivisions>

    @FormUrlEncoded
    @POST(GET_FOOTBALL_TEAMS)
    fun getFootballTeams(@FieldMap params: Map<String, String>):Single<ResponseFootballTeams>

}