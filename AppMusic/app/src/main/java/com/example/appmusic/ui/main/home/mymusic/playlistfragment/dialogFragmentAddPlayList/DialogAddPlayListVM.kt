package com.example.appmusic.ui.main.home.mymusic.playlistfragment.dialogFragmentAddPlayList

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class DialogAddPlayListVM :
    BaseViewModel() {
    private val musicRepository: MusicRepository = MusicRepository()
    var listPlayList = MutableLiveData<List<PlayList>>()

    fun allPlayList() {
        listPlayList.postValue(musicRepository.getAllPlayList())

    }

    fun insertPlayList(playList: PlayList?) {
        musicRepository.insertPlayList(playList)
    }
}
