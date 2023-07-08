package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.dialog

import com.example.tfmmusic.data.model.Music

@HiltViewModel
class BottomSheetAddPlayListVM @Inject constructor(musicRepository: MusicRepository) :
    BaseViewModel() {
    private val musicRepository: MusicRepository
    var listPlaylist: MutableLiveData<List<PlayList>> = MutableLiveData<List<PlayList>>()
    var listMusicPlaylist: MutableLiveData<List<Music>> = MutableLiveData<List<Music>>()

    init {
        this.musicRepository = musicRepository
    }

    fun UpdateNamePlayList(name: String?, id: Int) {
        musicRepository.UpdateNameMusic(name, id)
    }

    fun inSertMusicofPlayList(music: Music?) {
        musicRepository.insertMusicOfPlayList(music)
    }

    fun UpdatePlayList(name: String?, id: Int) {
        musicRepository.UpdateNameMusic(name, id)
    }

    val allPlayList: Unit
        get() {
            musicRepository.getAllPlayList().subscribe(object : SingleObserver<List<PlayList?>?>() {
                fun onSubscribe(@NonNull d: Disposable?) {
                    compositeDisposable.add(d)
                }

                fun onSuccess(@NonNull playLists: List<PlayList?>?) {
                    listPlaylist.postValue(playLists)
                }

                fun onError(@NonNull e: Throwable?) {}
            })
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