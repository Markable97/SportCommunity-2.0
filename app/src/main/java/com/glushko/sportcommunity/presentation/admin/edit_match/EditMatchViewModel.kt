package com.glushko.sportcommunity.presentation.admin.edit_match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.edit_match.model.ActionUI
import com.glushko.sportcommunity.domain.repository.admin.edit_match.EditMatchRepository
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMatchViewModel @Inject constructor(
    private val editMatchRepository: EditMatchRepository
) : ViewModel(){

    private val _liveDataAssignMatches = MutableLiveData<Result<List<MatchUI>>>()
    val liveDataAssignMatches: LiveData<Result<List<MatchUI>>> = _liveDataAssignMatches

    private val _liveDataSelectedMatch = MutableLiveData<MatchUI>()
    val liveDataSelectedMatch: LiveData<MatchUI> = _liveDataSelectedMatch

    private val _liveDataActions = MutableLiveData<Result<List<ActionUI>>>()
    val liveDataActions: LiveData<Result<List<ActionUI>>> = _liveDataActions

    init {
        viewModelScope.launch {
            _liveDataActions.postValue(editMatchRepository.getActions())
            _liveDataAssignMatches.postValue(editMatchRepository.getAssignMatches())
        }
    }

    fun setMatch(match: MatchUI) {
        _liveDataSelectedMatch.value = match
    }

}