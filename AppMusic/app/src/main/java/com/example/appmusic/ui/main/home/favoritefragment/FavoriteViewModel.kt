package com.example.appmusic.ui.main.home.favoritefragment

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel() {
    var listFavourite = MutableLiveData<MutableList<Music>>()
    fun getAllMusicFavourite(checkFavorite: Boolean) {
        listFavourite.postValue(musicRepository.getAllMusicFavourite(checkFavorite))

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