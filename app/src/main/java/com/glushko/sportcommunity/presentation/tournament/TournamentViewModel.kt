package com.glushko.sportcommunity.presentation.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.data.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.domain.repository.tournament.TournamentRepository
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TournamentViewModel @Inject constructor(
    private val tournamentRepository: TournamentRepository
): ViewModel() {

    private val _liveDataTable: MutableLiveData<List<TournamentTableDisplayData>> = MutableLiveData()
    val liveDataTable: LiveData<List<TournamentTableDisplayData>> = _liveDataTable

    private val _liveDataStatistics: MutableLiveData<List<PlayerStatisticAdapter>> = MutableLiveData()
    val liveDataStatistics: LiveData<List<PlayerStatisticAdapter>> = _liveDataStatistics

    fun init(){
        _liveDataTable.value = tournamentRepository.getTournamentTable()
        _liveDataStatistics.value = tournamentRepository.getStatistics()
    }

}