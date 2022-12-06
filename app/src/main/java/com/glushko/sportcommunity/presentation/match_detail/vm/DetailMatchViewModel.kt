package com.glushko.sportcommunity.presentation.match_detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glushko.sportcommunity.util.Result
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.match_detail.model.PlayerInMatchSegment
import com.glushko.sportcommunity.domain.repository.match_detail.MatchDetailRepository
import com.glushko.sportcommunity.presentation.base.BaseViewModel
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMatchViewModel @Inject constructor(
    private val matchDetailRepository: MatchDetailRepository,
): BaseViewModel() {

    private val _liveDataPlayersInMatch: MutableLiveData<Result<List<PlayerInMatchSegment>>> = MutableLiveData()
    val liveDataPlayersInMatch: LiveData<Result<List<PlayerInMatchSegment>>> = _liveDataPlayersInMatch

    fun getPlayersInMatch(matchId: Long, teamHomeId: Int){
        viewModelScope.launch {
            _liveDataPlayersInMatch.postValue(Result.Loading)
            _liveDataPlayersInMatch.postValue(matchDetailRepository.getMatchDetail(matchId, teamHomeId))
        }
    }

}