package com.glushko.sportcommunity.presentation.admin.schedule.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.StadiumUI
import com.glushko.sportcommunity.data.admin.schedule.stadium.model.toChooseModel
import com.glushko.sportcommunity.data.choose.model.ChooseModel
import com.glushko.sportcommunity.domain.admin.schedule.ScheduleRepository
import com.glushko.sportcommunity.util.EventLiveData
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private var stadiumsChooseModel: MutableList<ChooseModel> = mutableListOf()

    private val _liveDataStadiums = MutableLiveData<Result<List<StadiumUI>>>()
    val liveDataStadiums: LiveData<Result<List<StadiumUI>>> = _liveDataStadiums

    private val _eventSuccessCreateSchedule = EventLiveData<Result<String>>()
    val eventSuccessCreateSchedule: LiveData<Result<String>> = _eventSuccessCreateSchedule

    private var selectStadium: Int? = null
    private var selectDate: Long? = null
    private var selectTime: Pair<Int, Int>? = null

    init {
        getStadiumsFromServer()
    }

    private fun getStadiumsFromServer() {
        viewModelScope.launch {
            _liveDataStadiums.postValue(Result.Loading)
            val response = scheduleRepository.getStadiums()
            if (response is Result.Success) {
                stadiumsChooseModel = response.data.mapIndexed { index, stadiumUI ->
                    stadiumUI.toChooseModel(index)
                }.toMutableList()
            }
            _liveDataStadiums.postValue(response)
        }
    }

    fun getStadiums() = stadiumsChooseModel

    fun selectStadium(stadiumChoose: ChooseModel) {
        selectStadium = _liveDataStadiums.value?.data?.getOrNull(stadiumChoose.position ?: -1)?.id
    }

    fun selectDate(date: Long) {
        selectDate = date
    }

    fun selectTime(hour: Int, minute: Int) {
        selectTime = Pair(hour, minute)
        val time = selectTime!!
        Timber.d("Время Unix = время ${(time.first*60*60+time.second*60)}")
    }

    fun createSchedule(
        countGame: String,
        halfTime: String,
        timeHalfBreak: String,
        timeAfterBreak: String
    ) {
        val timeHalfBreakInt: Int = timeHalfBreak.toIntOrNull() ?: 0
        val timeAfterBreakInt = timeAfterBreak.toIntOrNull() ?: 0
        val countGameInt = countGame.toIntOrNull() ?: 0
        val halfTimeInt = halfTime.toIntOrNull() ?: 0
        if (countGameInt > 0 && halfTimeInt > 0 && selectDate != null && selectTime != null && selectStadium != null) {
            viewModelScope.launch {
                _eventSuccessCreateSchedule.postValue(Result.Loading)
                _eventSuccessCreateSchedule.postValue(
                    scheduleRepository.createSchedule(
                        date = selectDate!!,
                        time = selectTime!!,
                        stadiumId = selectStadium!!,
                        countGame = countGameInt,
                        halfTime = halfTimeInt,
                        halfBreakTime = timeHalfBreakInt,
                        betweenGameBreakTime = timeAfterBreakInt
                    )
                )
            }
        }
    }
}