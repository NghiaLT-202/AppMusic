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
class MusicViewModel  :
    BaseViewModel() {
    private val musicRepository: MusicRepository = MusicRepository()
    var listMusicDB = MutableLiveData<List<Music>>()

    fun getMusicSortDB() {
        listMusicDB.postValue(   musicRepository.getAllMusicSortDB())

    }

}