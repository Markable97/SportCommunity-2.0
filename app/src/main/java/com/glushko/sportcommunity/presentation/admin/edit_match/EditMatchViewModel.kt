package com.glushko.sportcommunity.presentation.admin.edit_match

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.edit_match.model.ActionUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PlayerWithActionUI
import com.glushko.sportcommunity.domain.repository.admin.edit_match.EditMatchRepository
import com.glushko.sportcommunity.util.EventLiveData
import com.glushko.sportcommunity.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMatchViewModel @Inject constructor(
    private val editMatchRepository: EditMatchRepository
) : ViewModel(){

    private val playersWithActions = mutableListOf<PlayerWithActionUI>()

    private val _liveDataPlayersWithActions = MutableLiveData<MutableList<PlayerWithActionUI>>(
        playersWithActions
    )
    val liveDataPlayersWithActions: LiveData<MutableList<PlayerWithActionUI>> = _liveDataPlayersWithActions

    private val _liveDataAssignMatches = MutableLiveData<Result<List<MatchUI>>>()
    val liveDataAssignMatches: LiveData<Result<List<MatchUI>>> = _liveDataAssignMatches

    private val _liveDataSelectedMatch = MutableLiveData<MatchUI>()
    val liveDataSelectedMatch: LiveData<MatchUI> = _liveDataSelectedMatch

    private val _liveDataActions = MutableLiveData<Result<List<ActionUI>>>()
    val liveDataActions: LiveData<Result<List<ActionUI>>> = _liveDataActions

    private val _eventAddAction = EventLiveData<Int>()
    val eventAddAction: LiveData<Int> = _eventAddAction

    private val _eventChangeUpdateButtonText = EventLiveData<Int>()
    val eventChangeUpdateButtonText: LiveData<Int> = _eventChangeUpdateButtonText

    private val _eventEnableScore = EventLiveData<Boolean>()
    val eventEnableScore: LiveData<Boolean> = _eventEnableScore

    private val _eventSaveResult = EventLiveData<Result<String>>()
    val eventSaveResult: LiveData<Result<String>> = _eventSaveResult


    init {
        viewModelScope.launch {
            _liveDataActions.postValue(editMatchRepository.getActions())
            _liveDataAssignMatches.postValue(editMatchRepository.getAssignMatches())
        }
    }

    fun setMatch(match: MatchUI) {
        _liveDataSelectedMatch.value = match
        buttonUpdateSetText(match.isSaved)
        _eventEnableScore.postValue(match.isSaved)
    }

    fun addAction() {
        playersWithActions.add(PlayerWithActionUI())
        _eventAddAction.postValue(playersWithActions.size)
    }

    fun createOrClearResult(goalsHome: String, goalsGuest: String) {
        val valueMatch = _liveDataSelectedMatch.value ?: return
        viewModelScope.launch {

            valueMatch.apply {
                teamHomeGoals = goalsHome.toInt()
                teamGuestGoals = goalsGuest.toInt()
                isSaved = !valueMatch.isSaved
            }
            buttonUpdateSetText(valueMatch.isSaved)
            _eventEnableScore.postValue(valueMatch.isSaved)
        }
    }

    private fun buttonUpdateSetText(isSaved: Boolean){
        val text = if (isSaved) R.string.edit_match__button_edit_result else R.string.edit_match__button_save_result
        _eventChangeUpdateButtonText.postValue(text)
    }

}
