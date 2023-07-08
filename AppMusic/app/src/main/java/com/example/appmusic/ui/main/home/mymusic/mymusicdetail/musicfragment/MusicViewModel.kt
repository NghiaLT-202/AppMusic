package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment

import com.example.tfmmusic.data.model.Music

@HiltViewModel
class MusicViewModel @Inject constructor(musicRepository: MusicRepository) : BaseViewModel() {
    private val musicRepository: MusicRepository
    var listMusicDB: MutableLiveData<List<Music>> = MutableLiveData<List<Music>>()

    init {
        this.musicRepository = musicRepository
    }

    val musicSortDB: Unit
        get() {
            musicRepository.getAllMusicSortDB().subscribe(object : SingleObserver<List<Music?>?>() {
                fun onSubscribe(@NonNull d: Disposable?) {}
                fun onSuccess(@NonNull music: List<Music?>?) {
                    listMusicDB.postValue(music)
                }

                fun onError(@NonNull e: Throwable?) {}
            })
        }
}