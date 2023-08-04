package com.example.appmusic.ui.main.home.favoritefragment

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listFavourite = MutableLiveData<MutableList<Music>>()
    fun getAllMusicFavourite(checkFavorite: Boolean) {
        listFavourite.postValue(musicRepository.getAllMusicFavourite(checkFavorite))

    }
}