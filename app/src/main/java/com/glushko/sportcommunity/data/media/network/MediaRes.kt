package com.glushko.sportcommunity.data.media.network

import com.glushko.sportcommunity.data.media.model.MediaUI
import com.google.gson.annotations.SerializedName

data class MediaRes(
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("sub_title")
    val subTitle: String,
    @SerializedName("preview")
    val preview: String
) {
    fun toModelUI() = MediaUI(
        matchId, title, subTitle, preview
    )
}
