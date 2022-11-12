package com.glushko.sportcommunity.data.admin.assign_matches.network

import com.google.gson.annotations.SerializedName

data class RequestMatchScore(
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("goal_home")
    val goalHome: Int?,
    @SerializedName("goal_guest")
    val goalGuest: Int?
)
