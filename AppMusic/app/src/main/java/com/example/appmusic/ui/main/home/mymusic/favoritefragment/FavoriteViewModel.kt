package com.example.appmusic.ui.main.home.mymusic.favoritefragment

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listFavourite = MutableLiveData<List<Music>>()
    fun getAllMusicFavourite(checkFavorite: Boolean) {
        musicRepository.getAllMusicFavourite(checkFavorite)
            .subscribe(object : SingleObserver<List<Music>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(list: List<Music>) {
                    listFavourite.postValue(list)
                }

                override fun onError(e: Throwable) {}
            })

//        listFavourite.postValue(musicRepository.getAllMusicFavourite(database.musicDao()));
//        musicRepository.getAllMusicFavourite(database.musicDao()).subscribe(new SingleObserver<List<Music>>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onSuccess(@NonNull List<Music> list) {
//                listFavourite.postValue(list);
//
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//
//            }
//        });
    }
}