package com.glushko.sportcommunity.presentation.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.data.statistics.model.TypeStatistics
import com.glushko.sportcommunity.domain.repository.tournament.TournamentRepository
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val tournamentRepository: TournamentRepository
): ViewModel() {

    private val _liveDataStatisticsPlayers = MutableLiveData<Resource<List<PlayerStatisticDisplayData>>>()
    val liveDataStatisticsPlayers: LiveData<Resource<List<PlayerStatisticDisplayData>>> = _liveDataStatisticsPlayers

    init {
        getStatistics(TypeStatistics.GOALS)
    }

    fun getStatistics(type: TypeStatistics){
        viewModelScope.launch {
            _liveDataStatisticsPlayers.postValue(Resource.Loading())
            _liveDataStatisticsPlayers.postValue(tournamentRepository.getStatisticsType(type))
        }

    }

}