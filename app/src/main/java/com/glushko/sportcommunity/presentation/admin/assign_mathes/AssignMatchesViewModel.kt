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
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.data
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

    private val _liveDataTours = MutableLiveData<Result<List<String>>>()

    private var divisionChooseModel: MutableList<ChooseModel> = mutableListOf()

    private var toursChooseModel: MutableList<ChooseModel> = mutableListOf()

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

    fun getTours() = toursChooseModel

    fun getToursFromServer(divisionChoose: ChooseModel) {
        viewModelScope.launch {
            val divisionUI = _liveDataDivisions.value?.data?.getOrNull(divisionChoose.position?:-1)
            divisionUI?.let { division ->
                _liveDataTours.postValue(Result.Loading)
                val response = assignMatchesRepository.getUnassignedTours(division.id)
                if(response is Result.Success){
                    toursChooseModel = response.data.mapIndexed { index, tour -> ChooseModel(
                        valueDisplay = tour,
                        valueType = Constants.TYPE_VALUE_TOUR,
                        position = index
                    ) }.toMutableList()
                }
            }
        }
    }

}