package com.example.appmusic.ui.main.home.playlistfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.data.model.DataPlayList
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class PlaylistViewModel  @Inject constructor(private val musicRepository: MusicRepository) : BaseViewModel(){
    var listDataMusicPlaylist = MutableLiveData<MutableList<DataMusic>>()


    fun insertPlayList(dataPlayList: DataPlayList) {
        musicRepository.insertPlayList(dataPlayList)
    }

    fun deletePlayList(name: String) {
        musicRepository.deletePlayList(name)
    }

    fun updateNamePlayList(name: String, id: Int) {
        musicRepository.updateNamePlayList(name, id)
    }

    fun getAllMusicPlayList(namePlayList: String) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler(fun(
            _: CoroutineContext, throwable: Throwable
        ) {
            run {
                Timber.e(throwable)
            }
        })) {
            listDataMusicPlaylist.postValue( musicRepository.getAllMusicPlayList(namePlayList))
        }


    }

}