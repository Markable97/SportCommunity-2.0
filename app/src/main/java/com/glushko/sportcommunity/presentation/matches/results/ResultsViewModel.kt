package com.glushko.sportcommunity.presentation.matches.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glushko.sportcommunity.presentation.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.domain.matches.MatchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository
): ViewModel() {
    private val _liveDataResults: MutableLiveData<List<MatchFootballDisplayData>> = MutableLiveData()
    val liveDataResults: LiveData<List<MatchFootballDisplayData>> = _liveDataResults

    fun init(){
        _liveDataResults.value = matchesRepository.getResults()
    }

}