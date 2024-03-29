package com.glushko.sportcommunity.presentation.main_screen.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.data.main_screen.model.ResponseMainScreen
import com.glushko.sportcommunity.domain.main_screen.MainRepository
import com.glushko.sportcommunity.presentation.base.BaseViewModel
import com.glushko.sportcommunity.presentation.tournament.model.AlreadyExistsFavoriteException
import com.glushko.sportcommunity.presentation.tournament.model.DivisionSelected
import com.glushko.sportcommunity.util.Constants.DELAY_SPLASH
import com.glushko.sportcommunity.util.EventLiveData
import com.glushko.sportcommunity.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.glushko.sportcommunity.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): BaseViewModel() {

    private val _eventSplashNavigate = EventLiveData<Result<Unit>>()
    val eventSplashNavigate: LiveData<Result<Unit>> = _eventSplashNavigate

    private val _liveDataLeagues: MutableLiveData<Resource<List<LeaguesDisplayData>>> = MutableLiveData()
    val liveDataLeagues: LiveData<Resource<List<LeaguesDisplayData>>> = _liveDataLeagues

    private val _liveDataSelectedDivision: MutableLiveData<DivisionSelected> = MutableLiveData()
    val liveDataSelectedDivision: LiveData<DivisionSelected> = _liveDataSelectedDivision

    private val _liveDataMainScreen = MutableLiveData<Resource<ResponseMainScreen>>()
    val liveDataMainScreen: LiveData<Resource<ResponseMainScreen>> = _liveDataMainScreen

    private val _eventShareUri = EventLiveData<Result<Uri>>()
    val eventShareUri: LiveData<Result<Uri>> = _eventShareUri

    private var jobMainScreen: Job? = null

    private var selectedLeague: Int? = null

    init {
        startPreload()
    }

    private fun startPreload() {
        viewModelScope.launch {
            val leaguesAsync = async {
                val response = getLeaguesInternal()
                _liveDataLeagues.postValue(response)
                response
            }
            val splashDelayAsync = async {
                delay(DELAY_SPLASH)
                true
            }
            val leaguesResponse = leaguesAsync.await()
            val splashDelay = splashDelayAsync.await()
            if (leaguesResponse is Resource.Success && splashDelay) {
                _eventSplashNavigate.postValue(Result.Success(Unit))
            } else {
                _eventSplashNavigate.postValue(Result.Error(leaguesResponse.error ?: Exception()))
            }
        }
    }

    private suspend fun getLeaguesInternal() = mainRepository.getLeagues()

    fun getLeagues() {
        viewModelScope.launch {
            _liveDataLeagues.postValue(
                getLeaguesInternal()
            )
        }
    }

    fun chooseDivision(divisionId: Int){
        _liveDataSelectedDivision.value = DivisionSelected(divisionId, mainRepository.getFavoriteDivision())
        if (jobMainScreen?.isActive == true) {
            jobMainScreen?.cancel()
        }
        jobMainScreen = viewModelScope.launch {
            _liveDataMainScreen.postValue(Resource.Loading())
            _liveDataMainScreen.postValue(mainRepository.getMainScreen(divisionId))
        }
    }

    fun getCalendarRetry(){
        _liveDataSelectedDivision.value?.let {
            chooseDivision(it.selectedId)
        }
    }

    fun getResultsRetry(){
        _liveDataSelectedDivision.value?.let {
            chooseDivision(it.selectedId)
        }
    }

    fun getUriToShare(bitmap: Bitmap, filePath: String, context: Context){
        val imagesFolder = File(filePath, "images")
        viewModelScope.launch(Dispatchers.IO) {
            _eventShareUri.postValue(Result.Loading)
            try {
                imagesFolder.mkdirs()
                val fileImage = File(imagesFolder, "${System.currentTimeMillis()}.png")
                val outputStream =  FileOutputStream(fileImage)
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
                outputStream.flush()
                outputStream.close()
                val uriToShare = FileProvider.getUriForFile(
                    context,
                    "com.glushko.sportcommunity.fileprovider",
                    fileImage
                )
                _eventShareUri.postValue(Result.Success(uriToShare))
            } catch (ex: Exception){
                _eventShareUri.postValue(Result.Error(ex))
                Timber.e(ex)
            }
        }
    }

    fun selectLeague(leagueId: Int){
        selectedLeague = leagueId
    }

    fun saveFavorite(isForced: Boolean = false) : Result<Boolean> {
        val leagueId = selectedLeague ?: return Result.Error(Exception())
        val divisionId = _liveDataSelectedDivision.value?.selectedId ?: return Result.Error(Exception())
        return if (mainRepository.getFavoriteDivision() != -1 && !isForced) {
            Result.Error(AlreadyExistsFavoriteException())
        } else {
            mainRepository.saveFavoriteTournament(leagueId, divisionId)
            Result.Success(true)
        }

    }

    fun deleteFavorite() {
        mainRepository.deleteFavoriteTournament()
    }

    fun getFavoriteDivision() = mainRepository.getFavoriteDivision()



    override fun onCleared() {
        super.onCleared()
        jobMainScreen?.cancel()
        jobMainScreen = null
    }

}