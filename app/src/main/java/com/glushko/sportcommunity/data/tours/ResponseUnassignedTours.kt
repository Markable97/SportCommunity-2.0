package com.glushko.sportcommunity.data.tours

import com.glushko.sportcommunity.data.network.BaseResponse

class ResponseUnassignedTours(
    success: Int,
    message: String,
    val tours: List<String> = listOf()
): BaseResponse(success, message)

fun ResponseUnassignedTours.toModel() = tours