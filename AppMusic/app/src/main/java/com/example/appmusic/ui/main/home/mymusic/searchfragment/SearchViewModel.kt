package com.example.appmusic.ui.main.home.mymusic.searchfragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listMusic = MutableLiveData<List<Music>>()
    fun getAllMusicSearch(context: Context?) {
        musicRepository.getMusicDevice(context).subscribe(object : SingleObserver<List<Music>> {
            override fun onSubscribe(d: Disposable) {}
            override fun onSuccess(list: List<Music>) {
                listMusic.postValue(list)
            }

            override fun onError(e: Throwable) {}
        })
    }
}