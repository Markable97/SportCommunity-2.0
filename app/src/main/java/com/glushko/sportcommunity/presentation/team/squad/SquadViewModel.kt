package com.glushko.sportcommunity.presentation.team.squad

import androidx.lifecycle.*
import com.glushko.sportcommunity.data.squad.model.SquadPlayerUI
import com.glushko.sportcommunity.data.squad.network.ResponseFootballSquad
import com.glushko.sportcommunity.domain.squad.SquadRepository
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SquadViewModel @Inject constructor(
    private val squadRepository: SquadRepository
) : ViewModel() {

    private var initialSquad: List<SquadPlayerUI> = emptyList()


    private val _liveDataSquadList: MutableLiveData<List<SquadPlayerUI>> = MutableLiveData()
    val liveDataSquadList: LiveData<List<SquadPlayerUI>> = _liveDataSquadList

    init {
        getSquad()
    }

    private fun getSquad(){
        initialSquad = squadRepository.getSquad()
        _liveDataSquadList.value = squadRepository.getSquad()
    }

    fun searchPlayer(text: String?) {
        if (text?.isNotBlank() == true){
            val newList = initialSquad.filter { it.playerName.contains(text) }
            _liveDataSquadList.value = newList
        } else {
            _liveDataSquadList.value = initialSquad
        }
    }


}