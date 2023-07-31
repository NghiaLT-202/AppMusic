package com.example.appmusic.ui.main.home.playlistfragment

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class PlaylistViewModel  @Inject constructor(private val musicRepository: MusicRepository) : BaseViewModel(){
    var listMusicPlaylist = MutableLiveData<MutableList<Music>>()


    fun insertPlayList(playList: PlayList) {
        musicRepository.insertPlayList(playList)
    }

    fun deletePlayList(name: String) {
        musicRepository.deletePlayList(name)
    }

    fun updateNamePlayList(name: String, id: Int) {
        musicRepository.updateNamePlayList(name, id)
    }

    fun getAllMusicPlayList(namePlayList: String) {
        listMusicPlaylist.postValue( musicRepository.getAllMusicPlayList(namePlayList))


    }

}