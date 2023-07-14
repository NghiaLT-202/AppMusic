package com.example.appmusic.ui.main.home.mymusic.mymusicdetail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicDetailViewModel :
    BaseViewModel() {
    private val musicRepository: MusicRepository=MusicRepository()
    var listMusic = MutableLiveData<MutableList<Music>>()
    var listFavourite = MutableLiveData<MutableList<Music>>()




    fun initData(context: Context) {
        listMusic.postValue(musicRepository.getMusicDevice(context))


    }

    fun insertFavorite(music: Music?) {
        musicRepository.insert(music)
    }

    fun deleteFavourite(path: String?) {
        musicRepository.deleteFavourite(path)
    }

    fun getAllFavourite(checkFavorite: Boolean) {
        listFavourite.postValue(musicRepository.getAllMusicFavourite(checkFavorite))

    }
}