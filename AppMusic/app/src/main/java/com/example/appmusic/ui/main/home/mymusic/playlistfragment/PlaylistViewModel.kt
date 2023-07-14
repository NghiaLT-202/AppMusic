package com.example.appmusic.ui.main.home.mymusic.playlistfragment

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable

@HiltViewModel
class PlaylistViewModel :
    BaseViewModel() {
    private val musicRepository: MusicRepository = MusicRepository()
    var listPlayList = MutableLiveData<MutableList<PlayList>>()
    var listMusicPlaylist = MutableLiveData<MutableList<Music>>()
    fun allPlayList() {
        listPlayList.postValue(musicRepository.getAllPlayList())
    }

    fun insertPlayList(playList: PlayList?) {
        musicRepository.insertPlayList(playList)
    }

    fun deletePlayList(name: String) {
        musicRepository.deletePlayList(name)
    }

    fun updateNamePlayList(name: String, id: Int) {
        musicRepository.updateNamePlayList(name, id)
    }

    fun getAllMusicPlayList(namePlayList: String) {
        listMusicPlaylist.postValue(  musicRepository.getAllMusicPlayList(namePlayList))


    }
}