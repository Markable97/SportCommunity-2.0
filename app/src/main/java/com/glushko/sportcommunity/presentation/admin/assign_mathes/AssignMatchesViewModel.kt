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
import com.glushko.sportcommunity.util.EventLiveData
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private val _liveDataUnassignedMatches = MutableLiveData<Result<List<MatchUI>>>()
    val liveDataUnassignedMatches: LiveData<Result<List<MatchUI>>> = _liveDataUnassignedMatches

    private val _liveDataCheckButtonAssign = MutableLiveData<Boolean>(false)
    val liveDataCheckButtonAssign: LiveData<Boolean> = _liveDataCheckButtonAssign

    private val _liveDataCheckButtonDelete = MutableLiveData<Boolean>(false)
    val liveDataCheckButtonDelete: LiveData<Boolean> = _liveDataCheckButtonDelete

    private val _eventAssignMatches = EventLiveData<Result<String>>()
    val eventAssignMatches: LiveData<Result<String>> = _eventAssignMatches

    private val _eventDeleteMatches = EventLiveData<Result<String>>()
    val eventDeleteMatches: LiveData<Result<String>> = _eventDeleteMatches

    private var selectDivision: ChooseModel? = null
    private var selectionTour: String? = null

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

    fun getUnassignedTours(divisionChoose: ChooseModel?) {
        viewModelScope.launch {
            val divisionUI = _liveDataDivisions.value?.data?.getOrNull(divisionChoose?.position?:-1)
            divisionUI?.let { division ->
                selectDivision = divisionChoose
                _liveDataTours.postValue(Result.Loading)
                val response = assignMatchesRepository.getUnassignedTours(division.id)
                if(response is Result.Success){
                    toursChooseModel = response.data.mapIndexed { index, tour -> ChooseModel(
                        valueDisplay = tour,
                        valueType = Constants.TYPE_VALUE_TOUR,
                        position = index
                    ) }.toMutableList()
                    _liveDataUnassignedMatches.postValue(Result.Success(emptyList()))
                }
                _liveDataTours.postValue(response)
            }
        }
    }

    fun getUnassignedMatches(tourChoose: ChooseModel){
        viewModelScope.launch {
            _liveDataTours.value?.data?.getOrNull(tourChoose.position ?: -1)?.let { tour ->
                selectionTour = tour
                val tournament = _liveDataDivisions.value?.data?.getOrNull(selectDivision?.position?:-1)
                if (tournament != null){
                    _liveDataUnassignedMatches.postValue(Result.Loading)
                    _liveDataUnassignedMatches.postValue(assignMatchesRepository.getUnassignedMatches(
                        tournamentId = tournament.id,
                        tours = tour)
                    )
                }
            }
        }
    }

    private fun getUnassignedMatchesAfterDeleting(deletingMatches: List<MatchUI>){
        val tournament = _liveDataDivisions.value?.data?.getOrNull(selectDivision?.position?:-1)
        if (selectionTour!= null && tournament != null){
            val unassignedMatches = _liveDataUnassignedMatches.value?.data ?: emptyList()
            if (unassignedMatches.isNotEmpty()){
                val newUnassignedMatches = deletingMatches.filter {
                    it.tournamentId == tournament.id && it.tour == selectionTour
                }.map { it.copy(isSelect = false) }

                if (newUnassignedMatches.isNotEmpty()){

                    _liveDataUnassignedMatches.postValue(Result.Success(unassignedMatches.plus(newUnassignedMatches)))
                }
            }
        }
    }

    fun checkButtonAssign() {
        _liveDataCheckButtonAssign.value = _liveDataUnassignedMatches.value?.data?.firstOrNull { it.isSelect } != null
    }

    fun checkButtonDelete() {
        _liveDataCheckButtonDelete.value = _liveDataAssignMatches.value?.data?.firstOrNull { it.isSelect } != null
    }

    fun assignMatches(){
        viewModelScope.launch {
            _eventAssignMatches.postValue(Result.Loading)
            val assignMatches = _liveDataUnassignedMatches.value?.data?.filter { it.isSelect }?: emptyList()
            val response = assignMatchesRepository.addAssignMatch(assignMatches)
            if (response is Result.Success){
                _liveDataUnassignedMatches.postValue(Result.Success(_liveDataUnassignedMatches.value?.data?.minus(
                    assignMatches.toSet()
                )?: emptyList()))
                getAssignMatches()
                checkButtonAssign()
            }
            _eventAssignMatches.postValue(response)
        }
    }

    fun deleteMatches() {
        viewModelScope.launch {
            _eventDeleteMatches.postValue(Result.Loading)
            val deletingMatches = _liveDataAssignMatches.value?.data?.filter { it.isSelect } ?: emptyList()
            val response = assignMatchesRepository.addAssignMatch(deletingMatches, true)
            if (response is Result.Success){
                _liveDataAssignMatches.postValue(
                    Result.Success(
                        _liveDataAssignMatches.value?.data?.minus(
                            deletingMatches.toSet()
                        ) ?: emptyList()
                    )
                )
                getUnassignedMatchesAfterDeleting(deletingMatches)
                getUnassignedTours(selectDivision)
                checkButtonDelete()
            }
            _eventDeleteMatches.postValue(response)
        }
    }

}