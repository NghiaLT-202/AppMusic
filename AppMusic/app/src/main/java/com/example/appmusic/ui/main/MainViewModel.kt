package com.example.appmusic.ui.main

import android.content.Context
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var musicRepository: MusicRepository): BaseViewModel() {
    var listAllMusicDevice = MutableLiveData<MutableList<Music>>()


    fun getAllMusicDetail(context: Context) {
        if (listAllMusicDevice.getValue() != null) {
            if (listAllMusicDevice.getValue()!!.size > 0) return
        }
        listAllMusicDevice.postValue( musicRepository.getMusicDevice(context))


    }

}