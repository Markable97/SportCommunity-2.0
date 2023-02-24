package com.glushko.sportcommunity.data.gallery.repository

import com.glushko.sportcommunity.data.datasource.network.ApiService
import com.glushko.sportcommunity.data.media.model.ImageUI
import com.glushko.sportcommunity.data.media.network.ImagesResMain
import com.glushko.sportcommunity.domain.gallery.GalleryRepository
import com.glushko.sportcommunity.util.NetworkUtils
import com.glushko.sportcommunity.util.Result
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

@BoundTo(supertype = GalleryRepository::class, component = SingletonComponent::class)
class GalleryRepositoryImpl @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val api: ApiService
) : GalleryRepository {

    override suspend fun getMatchMedia(matchId: Long): Result<List<ImageUI>> {
        val response = networkUtils.getResponseResult<ImagesResMain>(ImagesResMain::class.java){
            api.getMediaMatch(matchId)
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.images.map { it.toModelUI() })
        }
    }
}