package com.example.appmusic.ui.main.home.playlistfragment.detailPlayList

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailPlayListViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listSong = MutableLiveData<MutableList<Music>>()
    fun getAllMusicPlayList(name: String) {
        listSong.postValue(musicRepository.getAllDetailPlayListName(name))
    }
}