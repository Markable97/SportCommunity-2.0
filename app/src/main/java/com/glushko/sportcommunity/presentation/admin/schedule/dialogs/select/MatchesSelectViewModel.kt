package com.glushko.sportcommunity.presentation.admin.schedule.dialogs.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.domain.repository.admin.schedule.ScheduleRepository
import com.glushko.sportcommunity.util.data
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchesSelectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val scheduleRepository: ScheduleRepository
): ViewModel() {

    private val _liveDataAssignedMatches = MutableLiveData(savedStateHandle.get<Array<MatchUI>>("assignedMatches")?.toList() ?: emptyList())
    val liveDataAssignedMatches: LiveData<List<MatchUI>> = _liveDataAssignedMatches

    private val _liveDataCheckButtonAdd = MutableLiveData<Boolean>(false)
    val liveDataCheckButtonAdd: LiveData<Boolean> = _liveDataCheckButtonAdd

    private var selectedMatches: MatchUI? = null

    fun checkButtonAdd(match: MatchUI?) {
        selectedMatches = match
        _liveDataCheckButtonAdd.value = match != null
    }

}