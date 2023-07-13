package com.example.appmusic.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.model.Music
import com.example.appmusic.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    var liveData = MutableLiveData<ArrayList<Music>>()
}