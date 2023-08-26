package com.example.appmusic.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.DataItemRecent
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.data.model.DataPlayList
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(var musicRepository: MusicRepository) : BaseViewModel() {
    var listAllDataMusicDevice = MutableLiveData<MutableList<DataMusic>>()
    var listRecentLiveData = MutableLiveData<MutableList<DataItemRecent>>()
    var listPlaylistData = MutableLiveData<MutableList<DataPlayList>>()

    var isStartMedia = MutableLiveData<Boolean>()


    fun getAllMusicDetail(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            listAllDataMusicDevice.postValue(musicRepository.getMusicDevice(context))
        }
    }

    fun getAllRecentMusic() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler(fun(
            _: CoroutineContext, throwable: Throwable
        ) {
            run {
                Timber.e(throwable)
            }
        })) {
            listRecentLiveData.postValue(musicRepository.getAllRecentMusic())


        }
    }

    fun insertRecentMusic(dataItemRecent: DataItemRecent) {
        musicRepository.insertRecentMusic(dataItemRecent)
    }

    fun getAllPlayList() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler(fun(
            _: CoroutineContext, throwable: Throwable
        ) {
            run {
                Timber.e(throwable)
            }
        })) {
            listPlaylistData.postValue(musicRepository.getAllPlayList())
        }
    }


}