package com.glushko.sportcommunity.presentation.team.squad

import androidx.lifecycle.*
import com.glushko.sportcommunity.data.squad.model.SquadPlayerUI
import com.glushko.sportcommunity.domain.squad.SquadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SquadViewModel @Inject constructor(
    private val squadRepository: SquadRepository
) : ViewModel() {

    var initialSquad: List<SquadPlayerUI> = emptyList()

    init {
        getSquad()
    }

    private fun getSquad(){
        initialSquad = squadRepository.getSquad()
    }

    fun searchPlayer(text: String) : List<SquadPlayerUI> {
        return if (text.isNotBlank()){
            initialSquad.filter { it.playerName.contains(text) }
        } else {
            initialSquad
        }
    }


}