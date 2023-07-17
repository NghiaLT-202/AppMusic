package com.example.appmusic.ui.main.home.musicfragment

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicViewModel //
@Inject constructor( val musicRepository: MusicRepository) : BaseViewModel() {
    var listMusicDB = MutableLiveData<List<Music>>()
    //    public void getMusicSortDB() {
    //        musicRepository.getAllMusicSortDB().subscribe(new SingleObserver<List<Music>>() {
    //            @Override
    //            public void onSubscribe(@NonNull Disposable d) {
    //
    //            }
    //
    //            @Override
    //            public void onSuccess(@NonNull List<Music> music) {
    //                listMusicDB.postValue(music);
    //
    //            }
    //
    //            @Override
    //            public void onError(@NonNull Throwable e) {
    //
    //            }
    //        });
    //    }
}