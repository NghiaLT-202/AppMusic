package com.example.appmusic.ui.main.home.video

import com.example.tfmmusic.data.model.video.VideoRecent

@HiltViewModel
class VideoViewmodel @Inject constructor(youtubeRepository: YoutubeRepository) : BaseViewModel() {
    var youtubeRepository: YoutubeRepository
    var listRecentVideo: MutableLiveData<List<VideoRecent>> = MutableLiveData<List<VideoRecent>>()

    init {
        this.youtubeRepository = youtubeRepository
    }

    fun insertVideoRecent(videoRecent: VideoRecent?) {
        youtubeRepository.insertVideoRecent(videoRecent)
    }

    val allRecent: Unit
        get() {
            youtubeRepository.getAllRecent()
                .subscribe(object : SingleObserver<List<VideoRecent?>?>() {
                    fun onSubscribe(@NonNull d: Disposable?) {
                        compositeDisposable.add(d)
                    }

                    fun onSuccess(@NonNull videoRecents: List<VideoRecent?>?) {
                        listRecentVideo.postValue(videoRecents)
                    }

                    fun onError(@NonNull e: Throwable) {
                        e.printStackTrace()
                    }
                })
        }
}