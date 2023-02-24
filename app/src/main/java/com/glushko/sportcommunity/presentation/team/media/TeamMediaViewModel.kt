package com.glushko.sportcommunity.presentation.team.media

import androidx.lifecycle.*
import com.glushko.sportcommunity.data.media.model.MediaUI
import com.glushko.sportcommunity.domain.squad.SquadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.glushko.sportcommunity.util.Result
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class TeamMediaViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val squadRepository: SquadRepository
) : ViewModel() {

    private val _liveDataMedia: MutableLiveData<Result<List<MediaUI>>> = MutableLiveData()
    val liveDataMedia: LiveData<Result<List<MediaUI>>> = _liveDataMedia

    private val teamId: Int

    init {
        teamId = savedStateHandle["team_id"] ?: -1 //Указывается в nav_graph
        getMediaTeam()
    }

    fun getMediaTeam() {
        viewModelScope.launch {
            _liveDataMedia.postValue(Result.Loading)
            _liveDataMedia.postValue(squadRepository.getMedia(teamId))
        }
    }





}