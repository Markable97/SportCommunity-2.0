package com.glushko.sportcommunity.presentation.media

import androidx.lifecycle.*
import com.glushko.sportcommunity.data.media.model.ImageUI
import com.glushko.sportcommunity.domain.gallery.GalleryRepository
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _liveDataGallery = MutableLiveData<Result<List<ImageUI>>>()
    val liveDataGallery: LiveData<Result<List<ImageUI>>> = _liveDataGallery

    private val matchId: Long

    init {
        matchId = savedStateHandle.get("match_id") ?: -1 //Указывается в nav_graph
        getMatchMedia()
    }

    fun getMatchMedia(){
        viewModelScope.launch(Dispatchers.IO){
            _liveDataGallery.postValue(Result.Loading)
            _liveDataGallery.postValue(
                galleryRepository.getMatchMedia(matchId)
            )
        }
    }

}