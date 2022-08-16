package com.glushko.sportcommunity.presentation.tournament_table.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.tournament_table.model.TournamentTableDisplayData
import com.glushko.sportcommunity.domain.repository.tournament_table.TournamentTableRepository
import com.glushko.sportcommunity.presentation.BaseViewModel
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TournamentTableViewModel @Inject constructor(
    private val tournamentTableRepository: TournamentTableRepository
): BaseViewModel() {

    private val _liveDataTable: MutableLiveData<Resource<List<TournamentTableDisplayData>>> = MutableLiveData()
    val liveDataTable: LiveData<Resource<List<TournamentTableDisplayData>>> = _liveDataTable

    fun getTournamentTable(divisionId: Int){
        viewModelScope.launch {
            _liveDataTable.postValue(Resource.Loading())
            _liveDataTable.postValue(tournamentTableRepository.getTournamentTable(divisionId = divisionId))
        }
    }

}