package com.example.appmusic.ui.main.home.musicfragment.musicdetail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicDetailViewModel @Inject constructor(val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listMusic = MutableLiveData<MutableList<Music>>()
    var listFavourite = MutableLiveData<MutableList<Music>>()


    fun initData(context: Context) {
        listMusic.postValue(musicRepository.getMusicDevice(context))
    }
}