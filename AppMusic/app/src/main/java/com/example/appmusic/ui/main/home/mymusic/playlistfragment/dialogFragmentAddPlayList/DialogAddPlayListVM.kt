package com.example.appmusic.ui.main.home.mymusic.playlistfragment.dialogFragmentAddPlayList

import com.example.tfmmusic.data.model.PlayList

@HiltViewModel
class DialogAddPlayListVM @Inject constructor(musicRepository: MusicRepository) : BaseViewModel() {
    private val musicRepository: MusicRepository
    var listPlayList: MutableLiveData<List<PlayList>> = MutableLiveData<List<PlayList>>()

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
}