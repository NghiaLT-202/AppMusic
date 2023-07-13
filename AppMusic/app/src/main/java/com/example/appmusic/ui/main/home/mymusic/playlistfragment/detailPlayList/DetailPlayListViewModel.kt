package com.example.appmusic.ui.main.home.mymusic.playlistfragment.detailPlayList

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class DetailPlayListViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listSong = MutableLiveData<List<Music>>()
    fun getAllMusicPlayList(name: String?) {
        musicRepository.getAllDetailPlayListName(name)
            .subscribe(object : SingleObserver<List<Music>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(list: List<Music>) {
                    listSong.postValue(list)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }
}