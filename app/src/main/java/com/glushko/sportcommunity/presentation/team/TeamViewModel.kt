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
import com.glushko.sportcommunity.presentation.team.model.TeamDisplayData
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.data
import com.glushko.sportcommunity.util.succeeded
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import launchInMain

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

    private val _liveDataTeamDisplayData: MutableLiveData<Result<TeamDisplayData>> = MutableLiveData()
    val liveDataTeamDisplayData: LiveData<Result<TeamDisplayData>> = _liveDataTeamDisplayData

    fun loadingVisible(): Boolean {
        return !(_liveDataSquadInfo.value !is Result.Loading  && _liveDataTable.value !is Result.Loading)
    }

    fun init(teamId: Int){
        launchInMain()
            .doOnStart {
                _liveDataTeamDisplayData.value = Result.Loading
            }
            .onError {
                _liveDataTeamDisplayData.value = Result.Error(it as Exception)
            }
            .start {
                val squadDef = async { squadRepository.getSquadInfo(teamId) }
                val tournamentDef = async {
                    checkTournamentTable(teamId)
                }
                val squadResponse = squadDef.await()
                val tournamentResponse = tournamentDef.await()
                if (squadResponse.succeeded && tournamentResponse.succeeded) {
                    val statistics = squadRepository.getSquadStatistics()
                    _liveDataTeamDisplayData.value = Result.Success(
                        TeamDisplayData(
                            tournamentTable = (tournamentResponse as Result.Success).data,
                            statistics = statistics
                        )
                    )
                } else {
                    val error = (squadResponse as? Result.Error)?.exception
                        ?: (tournamentResponse as? Result.Error)?.exception
                        ?: Exception()
                    throw (error)
                }
            }
    }

    private suspend fun checkTournamentTable(teamId: Int): Result<List<TournamentTableDisplayData>>{
        val table = tournamentRepository.getTournamentTableTeam(teamId)
        return if (table.isEmpty()) {
            tournamentRepository.getTournamentTableForTeam(teamId)
        } else {
            Result.Success(table)
        }
    }

    fun getStatistics(){
        viewModelScope.launch {
            _liveDataStatistics.postValue(Resource.Success(squadRepository.getSquadStatistics()))
        }
    }

}