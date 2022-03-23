package com.glushko.sportcommunity.presentation.matches.calendar.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.domain.repository.matches.MatchesRepository
import com.glushko.sportcommunity.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val matchesRepository: MatchesRepository): BaseViewModel()  {

    private val _liveDataCalendar: MutableLiveData<List<MatchFootballDisplayData>> = MutableLiveData()
    val liveDataCalendar: LiveData<List<MatchFootballDisplayData>> = _liveDataCalendar

    fun getCalendar(divisionId: Int){
        disposable.add(matchesRepository.getCalendar(divisionId).subscribe({
            _liveDataCalendar.postValue(it)
        },{
            Timber.e("getCalendar ${it.message}")
        }))
    }

}