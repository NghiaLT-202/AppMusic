package com.example.appmusic.ui.main.home.mymusic.playlistfragment

import com.example.tfmmusic.data.model.Music

@HiltViewModel
class PlaylistViewModel @Inject constructor(musicRepository: MusicRepository) : BaseViewModel() {
    var listPlayList: MutableLiveData<List<PlayList>> = MutableLiveData<List<PlayList>>()
    var listMusicPlaylist: MutableLiveData<List<Music>> = MutableLiveData<List<Music>>()
    private val musicRepository: MusicRepository

    init {
        this.musicRepository = musicRepository
    }

    val allPlayList: Unit
        get() {
            musicRepository.getAllPlayList().subscribe(object : SingleObserver<List<PlayList?>?>() {
                fun onSubscribe(@NonNull d: Disposable?) {
                    compositeDisposable.add(d)
                }

                fun onSuccess(@NonNull playLists: List<PlayList?>?) {
                    listPlayList.postValue(playLists)
                }

                fun onError(@NonNull e: Throwable) {
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
            .subscribe(object : SingleObserver<List<Music?>?>() {
                fun onSubscribe(@NonNull d: Disposable?) {
                    compositeDisposable.add(d)
                }

                fun onSuccess(@NonNull playLists: List<Music?>?) {
                    listMusicPlaylist.postValue(playLists)
                }

                fun onError(@NonNull e: Throwable?) {}
            })
    }
}