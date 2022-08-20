package com.glushko.sportcommunity.presentation.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.load
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.databinding.ItemTournamentTableRowBinding
import com.glushko.sportcommunity.domain.repository.tournament.TournamentRepository
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val tournamentRepository: TournamentRepository
): ViewModel() {

    private val _liveDataTable: MutableLiveData<Resource<List<TournamentTableDisplayData>>> = MutableLiveData()
    val liveDataTable: LiveData<Resource<List<TournamentTableDisplayData>>> = _liveDataTable

    private val _liveDataStatistics: MutableLiveData<Resource<List<PlayerStatisticAdapter>>> = MutableLiveData()
    val liveDataStatistics: LiveData<Resource<List<PlayerStatisticAdapter>>> = _liveDataStatistics

    fun init(teamId: Int){
        viewModelScope.launch {
            _liveDataStatistics.postValue(tournamentRepository.getStatisticsTeam(teamId))
            _liveDataTable.postValue(tournamentRepository.getTournamentTableTeam(teamId))
        }
    }


}