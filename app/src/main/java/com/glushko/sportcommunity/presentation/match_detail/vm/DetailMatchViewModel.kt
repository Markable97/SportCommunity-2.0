package com.glushko.sportcommunity.presentation.match_detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.match_detail.model.PlayerDisplayData
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

    private val _liveDataPlayersInMatch: MutableLiveData<Resource<List<PlayerInMatchSegment>>> = MutableLiveData()
    val liveDataPlayersInMatch: LiveData<Resource<List<PlayerInMatchSegment>>> = _liveDataPlayersInMatch

    fun getPlayersInMatch(matchId: Long){
        viewModelScope.launch {
            _liveDataPlayersInMatch.postValue(Resource.Loading())
            _liveDataPlayersInMatch.postValue(matchDetailRepository.getMatchDetail(matchId))
        }
    }

}