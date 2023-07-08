package com.example.appmusic.ui.main.home.mymusic

import android.content.Context
import com.example.tfmmusic.data.model.Music

@HiltViewModel
class MyMusicViewmodel @Inject constructor(singerRepository: MusicRepository) : BaseViewModel() {
    private val singerRepository: MusicRepository
    var listArtist: MutableLiveData<List<Music>> = MutableLiveData<List<Music>>()

    init {
        this.singerRepository = singerRepository
    }

    fun getAllAudioFromDevice(context: Context?) {
        singerRepository.getMusicDevice(context)
            .subscribe(object : SingleObserver<List<Music?>?>() {
                fun onSubscribe(@NonNull d: Disposable?) {}
                fun onSuccess(@NonNull artists: List<Music?>?) {
                    listArtist.postValue(artists)
                }

                fun onError(@NonNull e: Throwable?) {}
            })
    }
}