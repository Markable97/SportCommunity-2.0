package com.glushko.sportcommunity.presentation.main_screen.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.domain.repository.main_screen.MainRepository
import com.glushko.sportcommunity.presentation.base.BaseViewModel
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): BaseViewModel() {

    private val _liveDataLeagues: MutableLiveData<Resource<List<LeaguesDisplayData>>> = MutableLiveData()
    val liveDataLeagues: LiveData<Resource<List<LeaguesDisplayData>>> = _liveDataLeagues

    private val _liveDataSelectedDivision: MutableLiveData<Int> = MutableLiveData()
    val liveDataSelectedDivision: LiveData<Int> = _liveDataSelectedDivision

    init {
        getLeagues()
    }

    fun getLeagues() {
        viewModelScope.launch {
            _liveDataLeagues.postValue(
                mainRepository.getLeagues()
            )
        }
    }

    fun chooseDivision(divisionId: Int){
        _liveDataSelectedDivision.value = divisionId
    }

}