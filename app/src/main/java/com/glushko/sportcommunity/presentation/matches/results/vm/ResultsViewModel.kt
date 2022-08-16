package com.glushko.sportcommunity.presentation.matches.results.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.matches.model.MatchFootballDisplayData
import com.glushko.sportcommunity.domain.repository.matches.MatchesRepository
import com.glushko.sportcommunity.presentation.BaseViewModel
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor( private val matchesRepository: MatchesRepository): BaseViewModel() {

    private val _liveDataResults: MutableLiveData<Resource<List<MatchFootballDisplayData>>> = MutableLiveData()
    val liveDataResults: LiveData<Resource<List<MatchFootballDisplayData>>> = _liveDataResults

    private var divisionId: Int? = null

    fun init(divisionId: Int){
        this.divisionId = divisionId
        getResults()
    }

    fun getResults(){
        divisionId?.let {
            viewModelScope.launch {
                _liveDataResults.postValue(Resource.Loading())
                _liveDataResults.postValue(matchesRepository.getResults(it))
            }
        }
    }

}