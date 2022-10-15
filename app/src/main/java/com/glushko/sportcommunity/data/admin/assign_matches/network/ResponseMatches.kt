package com.glushko.sportcommunity.data.admin.assign_matches.network

import com.glushko.sportcommunity.data.admin.assign_matches.model.Match
import com.glushko.sportcommunity.data.admin.assign_matches.model.toModel
import com.glushko.sportcommunity.data.network.BaseResponse
import com.google.gson.annotations.SerializedName

class ResponseMatches(
    success: Int,
    message: String,
    @SerializedName("matches")
    val matches: List<Match> = listOf()
): BaseResponse(success, message)

fun ResponseMatches.toModel() = matches.map { it.toModel() }
