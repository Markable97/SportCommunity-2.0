package com.glushko.sportcommunity.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {

    protected val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.dispose()
    }
}