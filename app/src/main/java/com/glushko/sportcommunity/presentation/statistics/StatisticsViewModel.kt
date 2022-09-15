package com.glushko.sportcommunity.presentation.statistics

import android.telecom.ConnectionService
import androidx.lifecycle.*
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.domain.repository.squad.SquadRepository
import com.glushko.sportcommunity.domain.repository.tournament.TournamentRepository
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tournamentRepository: TournamentRepository,
    private val squadRepository: SquadRepository
): ViewModel() {

    //Указывается в navigation в arguments фрагмениа, на которой делается навигация
    private val openFrom = savedStateHandle.get<Int>("openFrom")

    private val _liveDataStatisticsPlayers = MutableLiveData<Resource<List<PlayerStatisticDisplayData>>>()
    val liveDataStatisticsPlayers: LiveData<Resource<List<PlayerStatisticDisplayData>>> = _liveDataStatisticsPlayers

    init {
        getStatistics(TypeStatistics.GOALS)
    }

    fun getStatistics(type: TypeStatistics){
        viewModelScope.launch {
            _liveDataStatisticsPlayers.postValue(Resource.Loading())
            when(openFrom){
                Constants.OPEN_FROM_TOURNAMENT -> {
                    _liveDataStatisticsPlayers.postValue(tournamentRepository.getStatisticsType(type))
                }
                Constants.OPEN_FROM_TEAM -> {
                    _liveDataStatisticsPlayers.postValue(Resource.Error(error = Exception("Пока нет данных")))
                }
            }
        }

    }

}