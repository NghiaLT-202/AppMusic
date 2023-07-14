package com.example.appmusic.ui.main.home.mymusic

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
class MyMusicViewmodel @Inject constructor(private val singerRepository: MusicRepository) :
    BaseViewModel() {
    var listArtist = MutableLiveData<MutableList<Music>>()
    fun getAllAudioFromDevice(context: Context?) {
        listArtist.postValue(singerRepository.getMusicDevice(context))

    }
}