package com.glushko.sportcommunity.presentation.match_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glushko.sportcommunity.util.Result
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.match_detail.model.MatchSegment
import com.glushko.sportcommunity.data.match_detail.model.PlayerInMatchSegment
import com.glushko.sportcommunity.domain.match_detail.MatchDetailRepository
import com.glushko.sportcommunity.presentation.base.BaseViewModel
import com.glushko.sportcommunity.presentation.matches.model.MatchScreenType
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

    fun getPlayersInMatch(matchId: Long, teamHomeId: Int, screenType: MatchScreenType){
        viewModelScope.launch {
            _liveDataPlayersInMatch.postValue(Result.Loading)
            val response = matchDetailRepository.getMatchDetail(matchId, teamHomeId)
            when(screenType){
                MatchScreenType.TIME_LINE -> {
                    _liveDataPlayersInMatch.postValue(response)
                }
                MatchScreenType.LISTING -> {
                    if (response is Result.Success) {
                        val newList = response.data.filter { it.segment == MatchSegment.ACTION_HOME || it.segment == MatchSegment.ACTION_GUEST }
                        _liveDataPlayersInMatch.postValue(Result.Success(newList))
                    } else {
                        _liveDataPlayersInMatch.postValue(response)
                    }
                }
            }
        }
    }

}