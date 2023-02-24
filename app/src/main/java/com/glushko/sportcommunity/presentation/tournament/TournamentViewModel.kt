package com.glushko.sportcommunity.presentation.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glushko.sportcommunity.data.media.model.MediaUI
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.domain.tournament.TournamentRepository
import com.glushko.sportcommunity.presentation.tournament.model.TournamentInfoDisplayData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TournamentViewModel @Inject constructor(
    private val tournamentRepository: TournamentRepository
): ViewModel() {

    private val _liveDataTournamentInfo = MutableLiveData<TournamentInfoDisplayData>()
    val liveDataTournamentInfo: LiveData<TournamentInfoDisplayData> = _liveDataTournamentInfo

    private val _liveDataTable: MutableLiveData<List<TournamentTableDisplayData>> = MutableLiveData()
    val liveDataTable: LiveData<List<TournamentTableDisplayData>> = _liveDataTable

    private val _liveDataStatistics: MutableLiveData<List<PlayerStatisticAdapter>> = MutableLiveData()
    val liveDataStatistics: LiveData<List<PlayerStatisticAdapter>> = _liveDataStatistics

    private val _liveDataMedia: MutableLiveData<List<MediaUI>> = MutableLiveData()
    val liveDataMedia: LiveData<List<MediaUI>> = _liveDataMedia

    fun init(){
        _liveDataTournamentInfo.value = tournamentRepository.getTournamentInfo()
        _liveDataTable.value = tournamentRepository.getTournamentTable()
        _liveDataStatistics.value = tournamentRepository.getStatistics()
        _liveDataMedia.value = tournamentRepository.getMedia()
    }

}