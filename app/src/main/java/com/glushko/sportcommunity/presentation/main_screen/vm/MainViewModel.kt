package com.glushko.sportcommunity.presentation.main_screen.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.data.tournament_table.model.TournamentTableDisplayData
import com.glushko.sportcommunity.domain.repository.main_screen.MainRepository
import com.glushko.sportcommunity.domain.repository.matches.MatchesRepository
import com.glushko.sportcommunity.domain.repository.tournament_table.TournamentTableRepository
import com.glushko.sportcommunity.presentation.base.BaseViewModel
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val tournamentTableRepository: TournamentTableRepository,
    private val matchesRepository: MatchesRepository
): BaseViewModel() {

    private val _liveDataLeagues: MutableLiveData<Resource<List<LeaguesDisplayData>>> = MutableLiveData()
    val liveDataLeagues: LiveData<Resource<List<LeaguesDisplayData>>> = _liveDataLeagues

    private val _liveDataSelectedDivision: MutableLiveData<Int> = MutableLiveData()
    val liveDataSelectedDivision: LiveData<Int> = _liveDataSelectedDivision

    private val _liveDataTable: MutableLiveData<Resource<List<TournamentTableDisplayData>>> = MutableLiveData()
    val liveDataTable: LiveData<Resource<List<TournamentTableDisplayData>>> = _liveDataTable

    private val _liveDataCalendar: MutableLiveData<Resource<List<MatchFootballDisplayData>>> = MutableLiveData()
    val liveDataCalendar: LiveData<Resource<List<MatchFootballDisplayData>>> = _liveDataCalendar

    private val _liveDataResults: MutableLiveData<Resource<List<MatchFootballDisplayData>>> = MutableLiveData()
    val liveDataResults: LiveData<Resource<List<MatchFootballDisplayData>>> = _liveDataResults

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
            getCalendar(divisionId)
            getResults(divisionId)
            getTournamentTable(divisionId)
        }
    }

    private suspend fun getCalendar(divisionId: Int){
        _liveDataCalendar.postValue(Resource.Loading())
        _liveDataCalendar.postValue(matchesRepository.getCalendar(divisionId))
    }

    private suspend fun getResults(divisionId: Int){
        _liveDataResults.postValue(Resource.Loading())
        _liveDataResults.postValue(matchesRepository.getResults(divisionId))
    }

    private suspend fun getTournamentTable(divisionId: Int){
        _liveDataTable.postValue(Resource.Loading())
        _liveDataTable.postValue(tournamentTableRepository.getTournamentTable(divisionId = divisionId))
    }

    fun getCalendarRetry(){
        _liveDataSelectedDivision.value?.let {
            viewModelScope.launch{
                _liveDataCalendar.postValue(Resource.Loading())
                _liveDataCalendar.postValue(matchesRepository.getCalendar(it))
            }
        }
    }

    fun getTournamentTableRetry(){
        _liveDataSelectedDivision.value?.let {
            viewModelScope.launch{
                getTournamentTable(it)
            }
        }
    }

    fun getResultsRetry(){
        _liveDataSelectedDivision.value?.let {
            viewModelScope.launch {
                _liveDataResults.postValue(Resource.Loading())
                _liveDataResults.postValue(matchesRepository.getResults(it))
            }
        }
    }

}