package com.glushko.sportcommunity.presentation.results.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glushko.sportcommunity.data.results.model.MatchFootballDisplayData
import com.glushko.sportcommunity.domain.repository.results.ResultsRepository
import com.glushko.sportcommunity.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor( private val resultsRepository: ResultsRepository): BaseViewModel() {

    private val _liveDataResults: MutableLiveData<List<MatchFootballDisplayData>> = MutableLiveData()
    val liveDataResults: LiveData<List<MatchFootballDisplayData>> = _liveDataResults

    fun getResults(divisionId: Int){
        disposable.add(resultsRepository.getResults(divisionId).subscribe({
            _liveDataResults.postValue(it)
        },{
            Timber.e("Ошибка getResults ${it.message}")
        })
        )
    }

}