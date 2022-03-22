package com.glushko.sportcommunity.presentation.match_detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glushko.sportcommunity.data.match_detail.model.PlayerDisplayData
import com.glushko.sportcommunity.domain.repository.match_detail.MatchDetailRepository
import com.glushko.sportcommunity.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailMatchViewModel @Inject constructor(private val matchDetailRepository: MatchDetailRepository): BaseViewModel() {

    private val _liveDataPlayersInMatch: MutableLiveData<List<PlayerDisplayData>> = MutableLiveData()
    val liveDataPlayersInMatch: LiveData<List<PlayerDisplayData>> = _liveDataPlayersInMatch

    fun getPlayersInMatch(matchId: Long){
        disposable.add(
            matchDetailRepository.getMatchDetail(matchId).subscribe({
                _liveDataPlayersInMatch.postValue(it)
            }, {
                Timber.e("getPlayersInMatch ${it.message}")
            })
        )
    }

}