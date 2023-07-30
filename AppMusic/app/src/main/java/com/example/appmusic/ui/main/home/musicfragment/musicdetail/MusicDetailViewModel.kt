package com.example.appmusic.ui.main.home.musicfragment.musicdetail

import android.content.Context
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Random
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MusicDetailViewModel @Inject constructor(val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listMusic = MutableLiveData<MutableList<Music>>()
    var listFavourite = MutableLiveData<MutableList<Music>>()
    fun initData(context: Context) {
        listMusic.postValue(musicRepository.getMusicDevice(context))
    }    fun insertFavorite(music: Music) {
        musicRepository.insert(music)
    }
    fun deleteFavourite(path: String) {
        musicRepository.deleteFavourite(path)
    }
    fun getAllFavourite(checkFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler(fun(
            _: CoroutineContext, throwable: Throwable
        ) {
            run {
                Timber.e(throwable)
            }
        })) {
            listFavourite.postValue(musicRepository.getAllMusicFavourite(checkFavorite))


        }
    }

}