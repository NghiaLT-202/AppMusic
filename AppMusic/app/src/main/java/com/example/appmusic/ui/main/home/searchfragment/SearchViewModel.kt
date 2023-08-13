package com.example.appmusic.ui.main.home.searchfragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listMusic = MutableLiveData<MutableList<Music>>()
//    fun getAllMusicSearch(context: Context?) {
//        listMusic.postValue(musicRepository.getMusicDevice(context!!))
//    }
}