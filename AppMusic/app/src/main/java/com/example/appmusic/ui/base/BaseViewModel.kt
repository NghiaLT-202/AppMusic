package com.example.appmusic.ui.base

import androidx.lifecycle.ViewModelimport

dagger.hilt.android.lifecycle.HiltViewModelimport io.reactivex.rxjava3.disposables.CompositeDisposableimport javax.inject.Inject
//import dagger.hilt.android.lifecycle.HiltViewModel;
//import io.reactivex.rxjava3.disposables.CompositeDisposable;
@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    //    // Thu thaapj tat ca context hien tai dang su dung de huy bo, tranh viec memoryleak
    var compositeDisposable = CompositeDisposable()

    //
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}