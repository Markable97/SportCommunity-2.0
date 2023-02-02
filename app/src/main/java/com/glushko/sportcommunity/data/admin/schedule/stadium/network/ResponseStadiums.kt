package com.glushko.sportcommunity.data.admin.schedule.stadium.network

import com.glushko.sportcommunity.data.datasource.network.BaseResponse
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.Stadium

class ResponseStadiums(
    success:Int,
    message: String,
    val stadiums: List<Stadium> = emptyList()
): BaseResponse(success, message)