package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.dialog

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel
class BottomSheetAddPlayListVM :
    BaseViewModel() {
    private val musicRepository: MusicRepository = MusicRepository()
    var listPlaylist = MutableLiveData<MutableList<PlayList>>()
    var listMusicPlaylist = MutableLiveData<MutableList<Music>>()
    fun UpdateNamePlayList(name: String?, id: Int) {
        musicRepository.UpdateNameMusic(name, id)
    }

    fun inSertMusicofPlayList(music: Music?) {
        musicRepository.insertMusicOfPlayList(music)
    }

    fun UpdatePlayList(name: String?, id: Int) {
        musicRepository.UpdateNameMusic(name, id)
    }

    fun getAllPlayList() {
        listPlaylist.postValue(musicRepository.getAllPlayList())


    }

    fun getAllMusicPlayList(namePlayList: String) {
        listMusicPlaylist.postValue(musicRepository.getAllMusicPlayList(namePlayList))


    }
}