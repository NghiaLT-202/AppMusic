package com.example.appmusic.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var musicRepository: MusicRepository): BaseViewModel() {
    var listAllMusicDevice = MutableLiveData<MutableList<Music>>()
    var listRecentLiveData = MutableLiveData<MutableList<ItemRecent>>()

    var isStartMedia = MutableLiveData<Boolean>()


    fun getAllMusicDetail(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            listAllMusicDevice.postValue( musicRepository.getMusicDevice(context))
        }
    }
    fun getAllReccentMusic() {
        listRecentLiveData.postValue(  musicRepository.getAllRecentMusic())
    }
    fun insertReccentMusic(itemRecent: ItemRecent) {
        Timber.e("ltnghia"+itemRecent.musicName)
        musicRepository.insertRecentMusic(itemRecent)
    }


}