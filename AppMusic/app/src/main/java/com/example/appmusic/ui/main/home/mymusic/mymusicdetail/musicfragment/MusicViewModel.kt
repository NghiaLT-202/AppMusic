package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listMusicDB = MutableLiveData<List<Music>>()
    val musicSortDB: Unit
        get() {
            musicRepository.allMusicSortDB.subscribe(object : SingleObserver<List<Music>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(music: List<Music>) {
                    listMusicDB.postValue(music)
                }

                override fun onError(e: Throwable) {}
            })
        }
}