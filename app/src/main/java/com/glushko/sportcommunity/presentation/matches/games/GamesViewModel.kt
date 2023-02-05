package com.glushko.sportcommunity.presentation.matches.games

import androidx.lifecycle.*
import com.glushko.sportcommunity.domain.matches.MatchesRepository
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import dagger.hilt.android.lifecycle.HiltViewModel
import com.glushko.sportcommunity.util.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val matchesRepository: MatchesRepository
):ViewModel() {

    //Указывается в navigation в arguments фрагмениа, на которой делается навигация
    private val teamId = savedStateHandle.get<Int>("teamId")
    private val tournamentId = savedStateHandle.get<Int>("tournamentId")

    private val _liveDataGames = MutableLiveData<Result<List<MatchFootballDisplayData>>>()
    val liveDataGames: LiveData<Result<List<MatchFootballDisplayData>>> = _liveDataGames

    init {
        getMatches()
    }

    fun getMatches() {
        viewModelScope.launch {
            _liveDataGames.postValue(Result.Loading)
            _liveDataGames.postValue(matchesRepository.getMatches(teamId, tournamentId))
        }
    }

}