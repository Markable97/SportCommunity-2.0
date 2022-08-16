package com.glushko.sportcommunity.presentation.matches.calendar.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.domain.repository.matches.MatchesRepository
import com.glushko.sportcommunity.presentation.base.BaseViewModel
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val matchesRepository: MatchesRepository): BaseViewModel()  {

    private val _liveDataCalendar: MutableLiveData<Resource<List<MatchFootballDisplayData>>> = MutableLiveData()
    val liveDataCalendar: LiveData<Resource<List<MatchFootballDisplayData>>> = _liveDataCalendar

    private var divisionId: Int? = null

    fun init(divisionId: Int){
        this.divisionId = divisionId
        getCalendar()
    }

    fun getCalendar(){
        divisionId?.let {
            viewModelScope.launch{
                _liveDataCalendar.postValue(Resource.Loading())
                _liveDataCalendar.postValue(matchesRepository.getCalendar(it))
            }
        }
    }

}