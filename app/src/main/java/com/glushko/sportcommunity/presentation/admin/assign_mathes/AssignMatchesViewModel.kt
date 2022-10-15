package com.glushko.sportcommunity.presentation.admin.assign_mathes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.domain.repository.admin.assign_matches.AssignMatchesRepository
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignMatchesViewModel @Inject constructor(
    private val assignMatchesRepository: AssignMatchesRepository
): ViewModel() {

    private val _liveDataAssignMatches = MutableLiveData<Result<List<MatchUI>>>()
    val liveDataAssignMatches: LiveData<Result<List<MatchUI>>> = _liveDataAssignMatches

    init {
        getAssignMatches()
    }

    private fun getAssignMatches() {
        viewModelScope.launch {
            _liveDataAssignMatches.postValue(Result.Loading)
            _liveDataAssignMatches.postValue(assignMatchesRepository.getAssignMatches())
        }

    }

}