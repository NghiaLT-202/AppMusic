package com.example.appmusic.ui.main.home.mymusic.playlistfragment.detailPlayList

import com.example.tfmmusic.data.model.Music

@HiltViewModel
class DetailPlayListViewModel @Inject constructor(musicRepository: MusicRepository) :
    BaseViewModel() {
    private val musicRepository: MusicRepository
    var listSong: MutableLiveData<List<Music>> = MutableLiveData<List<Music>>()

    init {
        this.musicRepository = musicRepository
    }

    fun getAllMusicPlayList(name: String?) {
        musicRepository.getAllDetailPlayListName(name)
            .subscribe(object : SingleObserver<List<Music?>?>() {
                fun onSubscribe(@NonNull d: Disposable?) {
                    compositeDisposable.add(d)
                }

                fun onSuccess(@NonNull list: List<Music?>?) {
                    listSong.postValue(list)
                }

                fun onError(@NonNull e: Throwable) {
                    e.printStackTrace()
                }
            })
    }
}