package com.glushko.sportcommunity.data.squad.network

import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.data.media.network.MediaRes

class ResponseTeamMedia(
    success: Int,
    message: String,
    val media: List<MediaRes> = listOf()
) : BaseResponse(success, message)