package com.glushko.sportcommunity.presentation.main_screen.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.main_screen.leagues.network.ResponseFootballLeagues
import com.glushko.sportcommunity.domain.repository.main_screen.MainRepository
import com.glushko.sportcommunity.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): BaseViewModel() {

    private val _liveDataLeagues: MutableLiveData<List<LeaguesDisplayData>> = MutableLiveData()
    val liveDataLeagues: LiveData<List<LeaguesDisplayData>> = _liveDataLeagues

    private val _liveDataSelectedDivision: MutableLiveData<Int> = MutableLiveData()
    val liveDataSelectedDivision: LiveData<Int> = _liveDataSelectedDivision

    init {
        getLeagues()
    }

    private fun getLeagues() {
        disposable.add(mainRepository.getLeagues().subscribe({
            Timber.d("Пришли лиги = $it")
            _liveDataLeagues.postValue(it)
        },{
            Timber.e("Ошибка getLeagues ${it.message}")
        })
        )
    }

    fun chooseDivision(divisionId: Int){
        _liveDataSelectedDivision.value = divisionId
    }

}