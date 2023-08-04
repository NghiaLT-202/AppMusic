package com.example.appmusic.ui.main.home.musicfragment.dialogfragment.dialog

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
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class BottomSheetAddPlayListVM @Inject constructor( var musicRepository: MusicRepository) :
    BaseViewModel() {
    var listMusicPlaylist = MutableLiveData<MutableList<Music>>()
    fun updateNamePlayList(name: String, id: Int) {
        musicRepository.updateNameMusic(name, id)
    }

    fun inSertMusicofPlayList(music: Music) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler(fun(
            _: CoroutineContext, throwable: Throwable
        ) {
            run {
                Timber.e(throwable)
            }
        })) {
            musicRepository.insertMusicOfPlayList(music)
        }
    }




    fun getAllMusicPlayList(namePlayList: String) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler(fun(
            _: CoroutineContext, throwable: Throwable
        ) {
            run {
                Timber.e(throwable)
            }
        })) {
            listMusicPlaylist.postValue(musicRepository.getAllMusicPlayList(namePlayList))
        }

    }
}