package com.example.appmusic.ui.main.home.playlistfragment.detailPlayList

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
class DetailPlayListViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
     var listSong = MutableLiveData<MutableList<DataMusic>>()
    fun getAllMusicPlayList(name: String) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler(fun(
            _: CoroutineContext, throwable: Throwable
        ) {
            run {
                Timber.e(throwable)
            }
        })) {
            listSong.postValue(musicRepository.getAllDetailPlayListName(name))
        }
    }
}