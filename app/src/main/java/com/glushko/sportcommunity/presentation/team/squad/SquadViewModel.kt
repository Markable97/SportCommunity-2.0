package com.glushko.sportcommunity.presentation.team.squad

import androidx.compose.runtime.collection.MutableVector
import androidx.lifecycle.*
import com.glushko.sportcommunity.data.squad.model.SquadPlayer
import com.glushko.sportcommunity.domain.repository.squad.SquadRepository
import com.glushko.sportcommunity.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SquadViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val squadRepository: SquadRepository
) : ViewModel() {

    //Указывается в navigation в arguments фрагмениа, на которой делается навигация
    private val teamId = savedStateHandle.get<Int>("teamId")

    private var initialSquad: List<SquadPlayer> = emptyList()

    private val _liveDataSquadList: MutableLiveData<Resource<List<SquadPlayer>>> = MutableLiveData()
    val liveDataSquadList: LiveData<Resource<List<SquadPlayer>>> = _liveDataSquadList

    init {
        getSquadInfo(teamId)
    }

    private fun getSquadInfo(teamId: Int?) {
        Timber.d("squad teamId = $teamId")
        teamId?.let {
            viewModelScope.launch {
                val response = squadRepository.getSquad(it)
                if (response is Resource.Success){
                    initialSquad = response.data!!
                }
                _liveDataSquadList.postValue(response)
            }
        }
    }

    fun searchPlayer(text: String?) {
        if (text?.isNotBlank() == true){
            val newList = initialSquad.filter { it.playerName.contains(text) }
            _liveDataSquadList.value = Resource.Success(newList)
        } else {
            _liveDataSquadList.value = Resource.Success(initialSquad)
        }
    }


}