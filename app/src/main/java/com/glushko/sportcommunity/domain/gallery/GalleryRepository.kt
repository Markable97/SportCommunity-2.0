package com.glushko.sportcommunity.domain.gallery

import com.glushko.sportcommunity.data.media.model.ImageUI
import com.glushko.sportcommunity.util.Result

interface GalleryRepository {

    suspend fun getMatchMedia(matchId: Long): Result<List<ImageUI>>

}