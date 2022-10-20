package com.glushko.sportcommunity.presentation.admin.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.CalendarDayUI
import com.glushko.sportcommunity.domain.repository.admin.schedule.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
): ViewModel(){

    private val _liveDataCalendar = MutableLiveData<List<CalendarDayUI>>(scheduleRepository.getCalendar())
    val liveDataCalendar: LiveData<List<CalendarDayUI>> = _liveDataCalendar



}