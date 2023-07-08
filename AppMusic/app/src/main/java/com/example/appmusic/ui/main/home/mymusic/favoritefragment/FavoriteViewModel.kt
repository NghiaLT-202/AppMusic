package com.example.appmusic.ui.main.home.mymusic.favoritefragment

import com.example.tfmmusic.data.model.Music

@HiltViewModel
class FavoriteViewModel @Inject constructor(musicRepository: MusicRepository) : BaseViewModel() {
    private val musicRepository: MusicRepository
    var listFavourite: MutableLiveData<List<Music>> = MutableLiveData<List<Music>>()

    init {
        this.musicRepository = musicRepository
    }

    fun getAllMusicFavourite(checkFavorite: Boolean) {
        musicRepository.getAllMusicFavourite(checkFavorite)
            .subscribe(object : SingleObserver<List<Music?>?>() {
                fun onSubscribe(@NonNull d: Disposable?) {}
                fun onSuccess(@NonNull list: List<Music?>?) {
                    listFavourite.postValue(list)
                }

                fun onError(@NonNull e: Throwable?) {}
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