package com.glushko.sportcommunity.presentation.admin.schedule.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.StadiumUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.toChooseModel
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.domain.repository.admin.schedule.ScheduleRepository
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
): ViewModel() {

    private var stadiumsChooseModel: MutableList<ChooseModel> = mutableListOf()

    private val _liveDataStadiums = MutableLiveData<Result<List<StadiumUI>>>()
    val liveDataStadiums: LiveData<Result<List<StadiumUI>>> = _liveDataStadiums

    private var selectStadium: Int? = null

    init {
        getStadiumsFromServer()
    }

    private fun getStadiumsFromServer(){
        viewModelScope.launch {
            _liveDataStadiums.postValue(Result.Loading)
            val response = scheduleRepository.getStadiums()
            if(response is Result.Success){
               stadiumsChooseModel = response.data.mapIndexed { index, stadiumUI ->
                   stadiumUI.toChooseModel(index)
               }.toMutableList()
            }
            _liveDataStadiums.postValue(response)
        }
    }

    fun getStadiums() = stadiumsChooseModel

    fun selectStadium(stadiumChoose: ChooseModel){
        selectStadium = _liveDataStadiums.value?.data?.getOrNull(stadiumChoose.position?:-1)?.id
    }

}