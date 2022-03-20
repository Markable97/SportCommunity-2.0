package com.glushko.sportcommunity.presentation.tournament_table.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glushko.sportcommunity.data.tournament_table.model.TournamentTableDisplayData
import com.glushko.sportcommunity.domain.repository.tournament_table.TournamentTableRepository
import com.glushko.sportcommunity.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TournamentTableViewModel @Inject constructor(
    private val tournamentTableRepository: TournamentTableRepository
): BaseViewModel() {

    private val _liveDataTable: MutableLiveData<List<TournamentTableDisplayData>> = MutableLiveData()
    val liveDataTable: LiveData<List<TournamentTableDisplayData>> = _liveDataTable

    fun getTournamentTable(divisionId: Int){
        disposable.add(
            tournamentTableRepository.getTournamentTable(divisionId = divisionId).subscribe({
                Timber.d("Пришла турнирная таблица = $it")
                _liveDataTable.postValue(it)
            }, {
                Timber.e("Ошибка getLeagues ${it.message}")
            })
        )
    }

}