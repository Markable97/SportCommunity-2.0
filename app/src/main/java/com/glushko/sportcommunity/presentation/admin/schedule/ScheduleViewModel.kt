package com.glushko.sportcommunity.presentation.admin.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.CalendarDayUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.ScheduleUI
import com.glushko.sportcommunity.domain.repository.admin.schedule.ScheduleRepository
import com.glushko.sportcommunity.util.Result
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

    fun getSchedule(unixDate: Long){
        viewModelScope.launch {
            _liveDataSchedule.postValue(Result.Loading)
            _liveDataSchedule.postValue(scheduleRepository.getSchedule(unixDate))
        }
    }


}