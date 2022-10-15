package com.glushko.sportcommunity.data.divisions.network

import com.glushko.sportcommunity.data.divisions.model.Division
import com.glushko.sportcommunity.data.divisions.model.toModel
import com.glushko.sportcommunity.data.network.BaseResponse

class ResponseDivisions(
    success: Int,
    message: String,
    val divisions: List<Division> = listOf()
): BaseResponse(success, message)

fun ResponseDivisions.toModel() = divisions.map { it.toModel() }