package com.example.appmusic.ui.main.home.mymusic.mymusicdetail

import android.content.Context
import com.example.tfmmusic.data.model.Music

@HiltViewModel
class MusicDetailViewModel @Inject constructor(musicRepository: MusicRepository) : BaseViewModel() {
    private val musicRepository: MusicRepository
    var listMusic: MutableLiveData<List<Music>> = MutableLiveData<List<Music>>()
    var listFavourite: MutableLiveData<List<Music>> = MutableLiveData<List<Music>>()

    init {
        this.musicRepository = musicRepository
    }

    fun setListMusic(listMusic: MutableLiveData<List<Music?>?>) {
        this.listMusic = listMusic
    }

    fun setListFavourite(listFavourite: MutableLiveData<List<Music?>?>) {
        this.listFavourite = listFavourite
    }

    fun initData(context: Context?) {
        musicRepository.getMusicDevice(context).subscribe(object : SingleObserver<List<Music?>?>() {
            fun onSubscribe(@NonNull d: Disposable?) {}
            fun onSuccess(@NonNull list: List<Music?>?) {
                listMusic.postValue(list)
            }

            fun onError(@NonNull e: Throwable?) {}
        })
    }

    fun insertFavorite(music: Music?) {
        musicRepository.insert(music)
    }

    fun deleteFavourite(path: String?) {
        musicRepository.deleteFavourite(path)
    }

    fun getAllFavourite(checkFavorite: Boolean) {
        musicRepository.getAllMusicFavourite(checkFavorite)
            .subscribe(object : SingleObserver<List<Music?>?>() {
                fun onSubscribe(@NonNull d: Disposable?) {}
                fun onSuccess(@NonNull list: List<Music?>?) {
                    listFavourite.postValue(list)
                }

                fun onError(@NonNull e: Throwable?) {}
            })
    }
}