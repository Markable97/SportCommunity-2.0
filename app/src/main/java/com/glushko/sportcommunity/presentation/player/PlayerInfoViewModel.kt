package com.glushko.sportcommunity.presentation.player

import androidx.lifecycle.*
import com.glushko.sportcommunity.domain.player.PlayerRepository
import com.glushko.sportcommunity.presentation.player.model.ProfilePlayerUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.glushko.sportcommunity.util.Result

@HiltViewModel
class PlayerInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _liveDataInfoPlayer = MutableLiveData<Result<ProfilePlayerUI>>()
    val liveDataInfoPlayer: LiveData<Result<ProfilePlayerUI>> = _liveDataInfoPlayer

    init {
        //Указывается в navigation в arguments фрагмениа, на которой делается навигация
        savedStateHandle.get<Int>("player_id")?.let { id ->
            getPlayerInfo(id)
        }
    }

    fun getPlayerInfo(playerInt: Int) {
        viewModelScope.launch {
            _liveDataInfoPlayer.postValue(Result.Loading)
            _liveDataInfoPlayer.postValue(playerRepository.getPlayerInfo(playerInt))
        }
    }


}