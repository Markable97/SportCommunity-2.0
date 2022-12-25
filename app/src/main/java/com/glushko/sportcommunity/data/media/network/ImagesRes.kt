package com.glushko.sportcommunity.data.media.network

import com.glushko.sportcommunity.data.media.model.ImageUI
import com.glushko.sportcommunity.data.network.BaseResponse

class ImagesResMain(
    success: Int,
    message: String,
    val images: List<ImagesRes> = emptyList()

): BaseResponse(success, message)

data class ImagesRes(
    val name: String,
    val preview: String,
    val origin: String
){
    fun toModelUI() = ImageUI(
        imagePreview = preview,
        imageOrigin = origin
    )
}