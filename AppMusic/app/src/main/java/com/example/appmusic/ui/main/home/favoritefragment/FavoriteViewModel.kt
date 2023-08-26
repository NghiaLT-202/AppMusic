package com.example.appmusic.ui.main.home.favoritefragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listFavourite = MutableLiveData<MutableList<DataMusic>>()
    fun getAllMusicFavourite(checkFavorite: Boolean) {
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