package com.example.appmusic.ui.main.home.mymusic.playlistfragment

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listPlayList = MutableLiveData<List<PlayList>>()
    var listMusicPlaylist = MutableLiveData<List<Music>>()
    val allPlayList: Unit
        get() {
            musicRepository.allPlayList.subscribe(object : SingleObserver<List<PlayList>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(playLists: List<PlayList>) {
                    listPlayList.postValue(playLists)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
        }

    fun insertPlayList(playList: PlayList?) {
        musicRepository.insertPlayList(playList)
    }

    fun deletePlayList(name: String?) {
        musicRepository.deletePlayList(name)
    }

    fun updateNamePlayList(name: String?, id: Int) {
        musicRepository.updateNamePlayList(name, id)
    }

    fun getAllMusicPlayList(namePlayList: String?) {
        musicRepository.getAllMusicPlayList(namePlayList)
            .subscribe(object : SingleObserver<List<Music>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(playLists: List<Music>) {
                    listMusicPlaylist.postValue(playLists)
                }

                override fun onError(e: Throwable) {}
            })
    }
}