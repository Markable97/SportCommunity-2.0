package com.glushko.sportcommunity.presentation.admin.schedule.dialogs.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.StadiumUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.TimeScheduleUI
import com.glushko.sportcommunity.domain.admin.schedule.ScheduleRepository
import com.glushko.sportcommunity.util.EventLiveData
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    val eventDeletedMatch = EventLiveData<Result<String>>()

    fun deleteMatch(stadium: StadiumUI, match: TimeScheduleUI) {
        viewModelScope.launch {
            eventDeletedMatch.postValue(Result.Loading)
            eventDeletedMatch.postValue(scheduleRepository.deleteMatchInSchedule(
                stadiumId = stadium.id,
                gameDate = "${match.date} ${match.time}"
            ))
        }
    }

}