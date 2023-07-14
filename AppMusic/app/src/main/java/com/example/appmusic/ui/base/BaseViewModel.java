package com.example.appmusic.ui.base;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

//import dagger.hilt.android.lifecycle.HiltViewModel;
//import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class BaseViewModel extends ViewModel {
    //    // Thu thaapj tat ca context hien tai dang su dung de huy bo, tranh viec memoryleak
    public CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public BaseViewModel() {
    }

    //
    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
