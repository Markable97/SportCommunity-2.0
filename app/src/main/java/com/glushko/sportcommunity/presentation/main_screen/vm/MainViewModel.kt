package com.glushko.sportcommunity.presentation.main_screen.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.main_screen.leagues.network.ResponseFootballLeagues
import com.glushko.sportcommunity.domain.repository.main_screen.MainRepository
import com.glushko.sportcommunity.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): BaseViewModel() {

    private val _liveDataLeagues: MutableLiveData<List<LeaguesDisplayData>> = MutableLiveData()
    val liveDataLeagues: LiveData<List<LeaguesDisplayData>> = _liveDataLeagues

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

}