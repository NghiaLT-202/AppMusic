package com.example.appmusic.ui.main.home

import com.example.tfmmusic.data.model.Music

class HomeViewModel : BaseViewModel() {
    var liveData: MutableLiveData<ArrayList<Music>> = MutableLiveData<ArrayList<Music>>()
}