package com.glushko.sportcommunity.presentation.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.squad.network.ResponseFootballSquad
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.domain.squad.SquadRepository
import com.glushko.sportcommunity.domain.tournament.TournamentRepository
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.glushko.sportcommunity.util.Result
import timber.log.Timber

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val tournamentRepository: TournamentRepository,
    private val squadRepository: SquadRepository,
): ViewModel() {

    private val _liveDataTable: MutableLiveData<Result<List<TournamentTableDisplayData>>> = MutableLiveData()
    val liveDataTable: LiveData<Result<List<TournamentTableDisplayData>>> = _liveDataTable

    private val _liveDataSquadInfo: MutableLiveData<Result<ResponseFootballSquad>> = MutableLiveData()
    val liveDataSquadInfo: LiveData<Result<ResponseFootballSquad>> = _liveDataSquadInfo

    private val _liveDataStatistics: MutableLiveData<Resource<List<PlayerStatisticAdapter>>> = MutableLiveData()
    val liveDataStatistics: LiveData<Resource<List<PlayerStatisticAdapter>>> = _liveDataStatistics

    fun init(teamId: Int){
        viewModelScope.launch {
            _liveDataSquadInfo.postValue(Result.Loading)
            _liveDataSquadInfo.postValue(squadRepository.getSquadInfo(teamId))
            checkTournamentTable(teamId)
        }
    }

    private suspend fun checkTournamentTable(teamId: Int){
            val table = tournamentRepository.getTournamentTableTeam(teamId)
            if (table.isEmpty()) {
                _liveDataTable.postValue(Result.Loading)
                _liveDataTable.postValue(tournamentRepository.getTournamentTableForTeam(teamId))
            } else {
                _liveDataTable.postValue(Result.Success(table))
            }
    }

    fun getStatistics(){
        viewModelScope.launch {
            _liveDataStatistics.postValue(Resource.Success(squadRepository.getSquadStatistics()))
        }
    }

}