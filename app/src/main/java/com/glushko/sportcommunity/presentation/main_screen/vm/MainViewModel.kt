package com.glushko.sportcommunity.presentation.main_screen.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.main_screen.model.ResponseMainScreen
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.domain.repository.main_screen.MainRepository
import com.glushko.sportcommunity.domain.repository.matches.MatchesRepository
import com.glushko.sportcommunity.domain.repository.tournament.TournamentRepository
import com.glushko.sportcommunity.presentation.base.BaseViewModel
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): BaseViewModel() {

    private val _liveDataLeagues: MutableLiveData<Resource<List<LeaguesDisplayData>>> = MutableLiveData()
    val liveDataLeagues: LiveData<Resource<List<LeaguesDisplayData>>> = _liveDataLeagues

    private val _liveDataSelectedDivision: MutableLiveData<Int> = MutableLiveData()
    val liveDataSelectedDivision: LiveData<Int> = _liveDataSelectedDivision

    private val _liveDataMainScreen = MutableLiveData<Resource<ResponseMainScreen>>()
    val liveDataMainScreen: LiveData<Resource<ResponseMainScreen>> = _liveDataMainScreen

    init {
        getLeagues()
    }

    fun getLeagues() {
        viewModelScope.launch {
            _liveDataLeagues.postValue(
                mainRepository.getLeagues()
            )
        }
    }

    fun chooseDivision(divisionId: Int){
        _liveDataSelectedDivision.value = divisionId
        viewModelScope.launch {
            _liveDataMainScreen.postValue(Resource.Loading())
            _liveDataMainScreen.postValue(mainRepository.getMainScreen(divisionId))
        }
    }

    fun getCalendarRetry(){
        _liveDataSelectedDivision.value?.let {
            chooseDivision(it)
        }
    }

    fun getResultsRetry(){
        _liveDataSelectedDivision.value?.let {
            chooseDivision(it)
        }
    }

}