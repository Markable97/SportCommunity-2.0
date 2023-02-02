package com.glushko.sportcommunity.presentation.admin.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.presentation.admin.schedule.model.CalendarDayUI
import com.glushko.sportcommunity.domain.admin.schedule.ScheduleRepository
import com.glushko.sportcommunity.presentation.admin.schedule.model.ScheduleUI
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
): ViewModel(){

    private val _liveDataCalendar = MutableLiveData(scheduleRepository.getCalendar())
    val liveDataCalendar: LiveData<List<CalendarDayUI>> = _liveDataCalendar

    private val _liveDataSchedule = MutableLiveData<Result<List<ScheduleUI>>>()
    val liveDataSchedule: LiveData<Result<List<ScheduleUI>>> = _liveDataSchedule

    private val _liveDataAssignMatches = MutableLiveData<Result<MutableList<MatchUI>>>()
    val liveDataAssignMatches: LiveData<Result<MutableList<MatchUI>>> = _liveDataAssignMatches

    private val _eventAddMatchInSchedule = MutableLiveData<Int>()
    val eventAddMatchInSchedule: LiveData<Int> = _eventAddMatchInSchedule

    init {
        getAssignedMatches()
    }

    fun getSchedule(unixDate: Long){
        viewModelScope.launch {
            _liveDataSchedule.postValue(Result.Loading)
            _liveDataSchedule.postValue(scheduleRepository.getSchedule(unixDate))
        }
    }

    private fun getAssignedMatches(){
        viewModelScope.launch {
            _liveDataAssignMatches.postValue(Result.Loading)
            _liveDataAssignMatches.postValue(scheduleRepository.getAssignMatches())
        }
    }

    fun actionWithAssignedMatches(isDelete: Boolean, math: MatchUI?) {
        if (math == null) return
        _liveDataAssignMatches.value?.data?.let { assignedMatches ->
            if (isDelete){
                assignedMatches.add(math)
            } else {
                assignedMatches.remove(math)
            }
        }
    }


}