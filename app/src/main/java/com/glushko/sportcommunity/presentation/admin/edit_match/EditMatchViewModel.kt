package com.glushko.sportcommunity.presentation.admin.edit_match

import androidx.lifecycle.*
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.admin.edit_match.model.ActionUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PLayerUI
import com.glushko.sportcommunity.data.admin.edit_match.model.PlayerWithActionUI
import com.glushko.sportcommunity.data.admin.edit_match.model.toChooseModel
import com.glushko.sportcommunity.presentation.core.dialogs.dialog_choose.model.ChooseModel
import com.glushko.sportcommunity.domain.admin.edit_match.EditMatchRepository
import com.glushko.sportcommunity.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditMatchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val editMatchRepository: EditMatchRepository
) : ViewModel(){

    private var actions = listOf<ChooseModel>()
    private var playersWithActions = mutableListOf<PlayerWithActionUI>()

    private var playerHomeInMatchBase = setOf<PLayerUI>()
    private var playerHomeOutMatchBase = setOf<PLayerUI>()
    private var playerGuestInMatchBase = setOf<PLayerUI>()
    private var playerGuestOutMatchBase = setOf<PLayerUI>()

    private var playersHome = mutableListOf<PLayerUI>()
    private var playersGuest = mutableListOf<PLayerUI>()

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

    private val _liveDataPLayersTeamHome = MutableLiveData<List<ChooseModel>>()
    val liveDataPLayersTeamHome: LiveData<List<ChooseModel>> = _liveDataPLayersTeamHome

    private val _liveDataPLayersTeamGuest = MutableLiveData<List<ChooseModel>>()
    val liveDataPLayersTeamGuest: LiveData<List<ChooseModel>> = _liveDataPLayersTeamGuest

    private val _eventAddAction = EventLiveData<Int>()
    val eventAddAction: LiveData<Int> = _eventAddAction

    private val _eventDeleteAction = EventLiveData<Int>()
    val eventDeleteAction: LiveData<Int> = _eventDeleteAction

    private val _eventUpdateAction = EventLiveData<Int>()
    val eventUpdateAction: LiveData<Int> = _eventUpdateAction

    private val _eventChangeUpdateButtonText = EventLiveData<Int>()
    val eventChangeUpdateButtonText: LiveData<Int> = _eventChangeUpdateButtonText

    private val _eventEnableScore = EventLiveData<Boolean>()
    val eventEnableScore: LiveData<Boolean> = _eventEnableScore

    private val _eventSaveResult = EventLiveData<Result<String>>()
    val eventSaveResult: LiveData<Result<String>> = _eventSaveResult

    private val _eventSelectPlayer = EventLiveData<PLayerUI>()
    val eventSelectPlayer: LiveData<PLayerUI> = _eventSelectPlayer

    val match = savedStateHandle.get<MatchUI>("match") //Указывается в nav_graph

    init {
        viewModelScope.launch {
            val responseActions = editMatchRepository.getActions()
            if (responseActions is Result.Success){
                actions = responseActions.data.mapIndexed { index, actionUI ->
                    actionUI.toChooseModel(index)
                }
            }
            _liveDataActions.postValue(responseActions)
            val response = editMatchRepository.getAssignMatches(
                matchId = match?.matchId
            )
            _liveDataAssignMatches.postValue(response)
        }
    }

    fun getActions(): List<ChooseModel> = actions

    fun setMatch(match: MatchUI, fromMatchDetail: Boolean = false) {
        _liveDataSelectedMatch.value = match
        buttonUpdateSetText(match.isSaved)
        _eventEnableScore.postValue(match.isSaved)
        getPlayersForMatch(match.teamHomeId, match.teamGuestId, match.matchId, fromMatchDetail)
    }

    private fun getPlayersForMatch(teamHome: Int, teamGuest: Int, matchId: Long, fromMatchDetail: Boolean = false){
        viewModelScope.launch {
            val response = editMatchRepository.getPlayersForMatch(teamHome, teamGuest, matchId, fromMatchDetail)
            if (response is Result.Success) {
                playersHome.clear()
                playersGuest.clear()
                playersWithActions = response.data.second.toMutableList()
                _liveDataPlayersWithActions.postValue(playersWithActions)
                response.data.first.forEach { player ->
                    if (player.teamId == teamHome) {
                        playersHome.add(player)
                    } else {
                        playersGuest.add(player)
                    }
                }

                playerHomeInMatchBase = playersHome.filter { it.inMatch }.toSet()
                playerHomeOutMatchBase = playersHome.filter { !it.inMatch }.toSet()
                playerGuestInMatchBase = playersGuest.filter { it.inMatch }.toSet()
                playerGuestOutMatchBase = playersGuest.filter { !it.inMatch }.toSet()

                _liveDataPLayersTeamHome.postValue(
                    playersHome.mapIndexed { index, pLayerUI ->
                        pLayerUI.toMultiChooseModel(index)
                    }
                )
                _liveDataPLayersTeamGuest.postValue(
                    playersGuest.mapIndexed { index, pLayerUI ->
                    pLayerUI.toMultiChooseModel(index)
                })
            } else {
                _liveDataPLayersTeamGuest.postValue(emptyList())
                _liveDataPLayersTeamHome.postValue(emptyList())
            }
        }
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
            }
            if (!valueMatch.isSaved){
                val response = editMatchRepository.addScore(valueMatch)
                if (response is Result.Success) {
                    valueMatch.isSaved = true
                    buttonUpdateSetText(true)
                    _eventEnableScore.postValue(valueMatch.isSaved)
                }
                _eventSaveResult.postValue(response)
            } else {
                valueMatch.isSaved = false
                buttonUpdateSetText(false)
                _eventEnableScore.postValue(valueMatch.isSaved)
            }
        }
    }

    fun clearGoals(){
        val match = _liveDataSelectedMatch.value ?: return
        viewModelScope.launch {
            val playersWithGoals = playersWithActions.filter {
                it.action?.actionId in Constants.TYPE_ACTION_GOALS
            }
            val response = editMatchRepository.deletePlayersWithGoals(
                matchId = match.matchId,
                playersWithGoals = playersWithGoals
            )
            if (response.succeeded) {
                playersWithActions.removeAll(playersWithGoals)
                _liveDataPlayersWithActions.postValue(playersWithActions)
                createOrClearResult(
                    match.teamHomeGoals.toString() ,
                    match.teamGuestGoals.toString()
                )
            }
            _eventSaveResult.postValue(response)
        }

    }

    private fun buttonUpdateSetText(isSaved: Boolean){
        val text = if (isSaved) R.string.edit_match__button_edit_result else R.string.edit_match__button_save_result
        _eventChangeUpdateButtonText.postValue(text)
    }

    fun deleteAction(position: Int) {
        playersWithActions.removeAt(position)
        _eventDeleteAction.postValue(position)
    }

    fun saveAction(position: Int) {
        val selectedMatch = _liveDataSelectedMatch.value ?: return
        viewModelScope.launch {
            val response = editMatchRepository.addPlayerAction(
                matchId = selectedMatch.matchId,
                isAdd = true,
                playerWithAction = playersWithActions[position]
            )
            if (response.succeeded){
                playersWithActions[position].isSaving = true
                _eventUpdateAction.postValue(position)
            }
            _eventSaveResult.postValue(response)
        }
    }

    fun editPlayer(position: Int) {
        val selectedMatch = _liveDataSelectedMatch.value ?: return
        viewModelScope.launch {
            val response = editMatchRepository.addPlayerAction(
                matchId = selectedMatch.matchId,
                isAdd = false,
                playerWithAction = playersWithActions[position]
            )
            if (response.succeeded) {
                playersWithActions[position].isSaving = false
                _eventUpdateAction.postValue(position)
            }
            _eventSaveResult.postValue(response)
        }
    }

    fun setActionToPlayer(data: ChooseModel, position: Int) {
        playersWithActions[position].action = _liveDataActions.value?.data?.getOrNull(data.position ?: -1)
        _eventUpdateAction.postValue(position)
        actions.forEach {
            it.isChoose = false
        }
    }

    fun setTimeToPLayer(minute: String, second: String, position: Int) {
        playersWithActions[position].time = "$minute:$second"
        _eventUpdateAction.postValue(position)
    }

    fun selectPlayer(data: ChooseModel, isHome: Boolean) {
        val player = if (isHome) {
            playersHome.getOrNull(data.position?:-1)
        } else {
            playersGuest.getOrNull(data.position?:-1)
        }
        _eventSelectPlayer.postValue(player ?: return)
    }

    fun setPlayerWithAction( player: PLayerUI, position: Int, isAssistant: Boolean) {
        if (isAssistant) {
            playersWithActions[position].playerAssist = player
        } else {
            playersWithActions[position].player = player
        }
        _eventUpdateAction.postValue(position)
        _liveDataPLayersTeamHome.value?.forEach {
            it.isChoose = false
        }
        _liveDataPLayersTeamGuest.value?.forEach {
            it.isChoose = false
        }
    }

    fun addPlayerForProtocol(data: ChooseModel, isHome: Boolean){
        data.position?.let { position ->
            if (isHome){
                playersHome[position].inMatch = data.isChoose
            } else {
                playersGuest[position].inMatch = data.isChoose
            }
        }
    }

    fun checkPlayersEmptyForProtocol() : Boolean{
        val isNotEmptyHome = playersHome.any { it.inMatch }
        val isNotEmptyGuest = playersGuest.any { it.inMatch }
        return isNotEmptyHome && isNotEmptyGuest
    }

    private fun getPlayerHomeForInsert(): List<PLayerUI>{
        val newPLayerHomeInsert = playersHome.filter { it.inMatch }.toMutableList()
        newPLayerHomeInsert.minusAssign(playerHomeInMatchBase)
        return  newPLayerHomeInsert
    }

    private fun getPayerHomeForeDelete(): List<PLayerUI>{
        val newPlayerDelete = playersHome.filter { !it.inMatch }.toMutableList()
        newPlayerDelete.minusAssign(playerHomeOutMatchBase)
        Timber.d("newPlayerDelete home = $newPlayerDelete")
        return newPlayerDelete
    }
    private fun getPlayerGuestForInsert(): List<PLayerUI>{
        val newPLayerHomeInsert = playersGuest.filter { it.inMatch }.toMutableList()
        newPLayerHomeInsert.minusAssign(playerGuestInMatchBase)
        return  newPLayerHomeInsert
    }

    private fun getPayerGuestForeDelete(): List<PLayerUI>{
        val newPlayerDelete = playersGuest.filter { !it.inMatch }.toMutableList()
        newPlayerDelete.minusAssign(playerGuestOutMatchBase)
        Timber.d("newPlayerDelete guest = $newPlayerDelete")
        return newPlayerDelete
    }

    fun addPlayerInMatch() {
        val matchId = _liveDataSelectedMatch.value?.matchId ?: return
        viewModelScope.launch {
            _eventSaveResult.postValue(Result.Loading)
            val playersHomeForInsert = getPlayerHomeForInsert()
            val playersHomeForDelete = getPayerHomeForeDelete()
            val playersGuestForInsert = getPlayerGuestForInsert()
            val playersGuestForDelete = getPayerGuestForeDelete()
            val playersForInsert = mutableListOf<PLayerUI>().apply {
                addAll(playersHomeForInsert)
                addAll(playersGuestForInsert)
            }
            val playersForDelete = mutableListOf<PLayerUI>().apply {
                addAll(playersHomeForDelete)
                addAll(playersGuestForDelete)
            }
            val responseInsert = editMatchRepository.addPlayersInMatch(
                matchId = matchId,
                players = playersForInsert
            )
            val responseDelete = editMatchRepository.deletePlayersInMatch(
                matchId = matchId,
                players = playersForDelete
            )
            if (responseInsert.succeeded && responseDelete.succeeded){
                _eventSaveResult.postValue(responseInsert)
                _liveDataPLayersTeamHome.postValue(
                    playersHome.filter { it.inMatch }.mapIndexed { index, pLayerUI ->
                        pLayerUI.toChooseModel(index)
                    }
                )
                _liveDataPLayersTeamGuest.postValue(
                    playersGuest.filter { it.inMatch }.mapIndexed { index, pLayerUI ->
                        pLayerUI.toChooseModel(index)
                    }
                )
            }
        }
    }

}
