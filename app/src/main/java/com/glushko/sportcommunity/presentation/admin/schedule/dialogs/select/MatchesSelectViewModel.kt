package com.glushko.sportcommunity.presentation.admin.schedule.dialogs.select

import androidx.lifecycle.*
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.StadiumUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.TimeScheduleUI
import com.glushko.sportcommunity.domain.repository.admin.schedule.ScheduleRepository
import com.glushko.sportcommunity.util.EventLiveData
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesSelectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _liveDataAssignedMatches = MutableLiveData(
        savedStateHandle.get<Array<MatchUI>>("assignedMatches")?.toList() ?: emptyList()
    )
    val liveDataAssignedMatches: LiveData<List<MatchUI>> = _liveDataAssignedMatches

    private val _liveDataCheckButtonAdd = MutableLiveData<Boolean>(false)
    val liveDataCheckButtonAdd: LiveData<Boolean> = _liveDataCheckButtonAdd

    val eventSuccessAddedMatch = EventLiveData<Result<String>>()

    private var selectedMatches: MatchUI? = null

    fun checkButtonAdd(match: MatchUI?) {
        selectedMatches = match
        _liveDataCheckButtonAdd.value = match != null
    }

    fun addMatchInSchedule(stadium: StadiumUI, time: TimeScheduleUI) {
        selectedMatches?.let { match ->
            viewModelScope.launch {
                eventSuccessAddedMatch.postValue(Result.Loading)
                eventSuccessAddedMatch.postValue(
                    scheduleRepository.addMatchInSchedule(
                        stadiumId = stadium.id,
                        gameDate = "${time.date} ${time.time}",
                        matchId = match.matchId
                    )
                )
            }
        }
    }

}