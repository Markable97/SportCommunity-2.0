package com.glushko.sportcommunity.presentation.admin.assign_mathes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.admin.assign_matches.model.MatchUI
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.data.divisions.model.DivisionUI
import com.glushko.sportcommunity.data.divisions.model.toChooseModel
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

    private val _liveDataDivisions = MutableLiveData<Result<List<DivisionUI>>>()
    val liveDataDivisions: LiveData<Result<List<DivisionUI>>> = _liveDataDivisions

    private var divisionChooseModel: MutableList<ChooseModel> = mutableListOf()

    init {
        viewModelScope.launch {
            getAssignMatches()
            getDivisionsFromServer()
        }
    }

    private suspend fun getAssignMatches() {
        _liveDataAssignMatches.postValue(Result.Loading)
        _liveDataAssignMatches.postValue(assignMatchesRepository.getAssignMatches())
    }

    private suspend fun getDivisionsFromServer(){
        _liveDataDivisions.postValue(Result.Loading)
        val response = assignMatchesRepository.getDivisions()
        if(response is Result.Success){
            divisionChooseModel = response.data.mapIndexed { index, divisionUI ->
                divisionUI.toChooseModel(index)
            }.toMutableList()
        }
        _liveDataDivisions.postValue(assignMatchesRepository.getDivisions())
    }

    fun getDivisions() = divisionChooseModel

}