package com.example.appmusic.ui.base

import javax.inject.Inject

//import dagger.hilt.android.lifecycle.HiltViewModel;
//import io.reactivex.rxjava3.disposables.CompositeDisposable;
@HiltViewModel
class BaseViewModel @Inject constructor() : ViewModel() {
    //    // Thu thaapj tat ca context hien tai dang su dung de huy bo, tranh viec memoryleak
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    //
    protected override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}