package com.glushko.sportcommunity.presentation.matches.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.domain.matches.MatchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository
): ViewModel() {
    private val _liveDataCalendar: MutableLiveData<List<MatchFootballDisplayData>> = MutableLiveData()
    val liveDataCalendar: LiveData<List<MatchFootballDisplayData>> = _liveDataCalendar

    fun init(){
        _liveDataCalendar.value = matchesRepository.getCalendar()
    }
}