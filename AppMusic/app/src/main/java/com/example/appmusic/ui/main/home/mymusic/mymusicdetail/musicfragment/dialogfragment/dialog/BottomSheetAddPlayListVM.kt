package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.dialog

import androidx.lifecycle.MutableLiveDataimport

com.example.appmusic.data .model.Musicimport com.example.appmusic.data .model.PlayListimport com.example.appmusic.data .repository.MusicRepositoryimport com.example.appmusic.ui.base.BaseViewModelimport dagger.hilt.android.lifecycle.HiltViewModelimport io.reactivex.rxjava3.core.SingleObserverimport io.reactivex.rxjava3.disposables.Disposableimport javax.inject.Inject
@HiltViewModel
class BottomSheetAddPlayListVM @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listPlaylist = MutableLiveData<List<PlayList>>()
    var listMusicPlaylist = MutableLiveData<List<Music>>()
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
            musicRepository.allPlayList.subscribe(object : SingleObserver<List<PlayList>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(playLists: List<PlayList>) {
                    listPlaylist.postValue(playLists)
                }

                override fun onError(e: Throwable) {}
            })
        }

    fun getAllMusicPlayList(namePlayList: String?) {
        musicRepository.getAllMusicPlayList(namePlayList)
            .subscribe(object : SingleObserver<List<Music>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(playLists: List<Music>) {
                    listMusicPlaylist.postValue(playLists)
                }

                override fun onError(e: Throwable) {}
            })
    }
}