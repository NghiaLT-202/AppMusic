package com.example.appmusic.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.appmusic.common.CommonEvent
import com.example.appmusic.common.LiveEvent
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class MainViewModel : BaseViewModel() {
    var musicRepository: MusicRepository?=null
    var isStartMedia = MutableLiveData<Boolean?>()
    var listAllMusicDevice = MutableLiveData<List<Music>?>()
    var event = LiveEvent<CommonEvent>()
    var liveEvent = LiveEvent<MessageEvent>()
    var listRecentLiveData = MutableLiveData<List<ItemRecent>>()
    fun getAllMusicDetail(context: Context) {
        if (listAllMusicDevice.value != null) {
            if (listAllMusicDevice.value!!.isNotEmpty()) return
        }
        musicRepository?.getMusicDevice(context)
    }

    fun insertReccentMusic(itemRecent: ItemRecent?) {
        musicRepository?.insertRecentMusic(itemRecent)
    }

    fun allReccentMusic() {
            listRecentLiveData.postValue( musicRepository?.allRecentMusic())

        }
}