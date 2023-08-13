package com.example.appmusic.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
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
    var listAllMusicDevice = MutableLiveData<MutableList<Music>>()
    var listRecentLiveData = MutableLiveData<MutableList<ItemRecent>>()
    var listPlaylist = MutableLiveData<MutableList<PlayList>>()

    var isStartMedia = MutableLiveData<Boolean>()


    fun getAllMusicDetail(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            listAllMusicDevice.postValue(musicRepository.getMusicDevice(context))
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

    fun insertRecentMusic(itemRecent: ItemRecent) {
        musicRepository.insertRecentMusic(itemRecent)
    }

    fun getAllPlayList() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler(fun(
            _: CoroutineContext, throwable: Throwable
        ) {
            run {
                Timber.e(throwable)
            }
        })) {
            listPlaylist.postValue(musicRepository.getAllPlayList())
        }
    }


}