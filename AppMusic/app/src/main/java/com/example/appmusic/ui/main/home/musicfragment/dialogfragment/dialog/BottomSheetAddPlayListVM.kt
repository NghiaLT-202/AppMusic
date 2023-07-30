package com.example.appmusic.ui.main.home.musicfragment.dialogfragment.dialog

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BottomSheetAddPlayListVM @Inject constructor( var musicRepository: MusicRepository) :
    BaseViewModel() {
    var listPlaylist = MutableLiveData<MutableList<PlayList>>()
    var listMusicPlaylist = MutableLiveData<MutableList<Music>>()
    fun updateNamePlayList(name: String, id: Int) {
        musicRepository.UpdateNameMusic(name, id)
    }

    fun inSertMusicofPlayList(music: Music) {
        musicRepository.insertMusicOfPlayList(music)
    }


    fun getAllPlayList() {
        listPlaylist.postValue( musicRepository.getAllPlayList())


    }

    fun getAllMusicPlayList(namePlayList: String) {
        listMusicPlaylist.postValue(musicRepository.getAllMusicPlayList(namePlayList))
    }
}