package com.example.appmusic.ui.main.home.mymusic.searchfragment

import android.content.Context
import com.example.tfmmusic.data.model.Music

@HiltViewModel
class SearchViewModel @Inject constructor(musicRepository: MusicRepository) : BaseViewModel() {
    private val musicRepository: MusicRepository
    var listMusic: MutableLiveData<List<Music>> = MutableLiveData<List<Music>>()

    init {
        this.musicRepository = musicRepository
    }

    fun getAllMusicSearch(context: Context?) {
        musicRepository.getMusicDevice(context).subscribe(object : SingleObserver<List<Music?>?>() {
            fun onSubscribe(@NonNull d: Disposable?) {}
            fun onSuccess(@NonNull list: List<Music?>?) {
                listMusic.postValue(list)
            }

            fun onError(@NonNull e: Throwable?) {}
        })
    }
}