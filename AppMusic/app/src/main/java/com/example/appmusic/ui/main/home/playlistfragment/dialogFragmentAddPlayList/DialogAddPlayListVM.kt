package com.example.appmusic.ui.main.home.playlistfragment.dialogFragmentAddPlayList

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DialogAddPlayListVM @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listPlayList = MutableLiveData<MutableList<PlayList>>()
    val allPlayList: Unit
        get() {
            listPlayList.postValue(musicRepository.getAllPlayList())
        }

    fun insertPlayList(playList: PlayList) {
        musicRepository.insertPlayList(playList)
    }
}