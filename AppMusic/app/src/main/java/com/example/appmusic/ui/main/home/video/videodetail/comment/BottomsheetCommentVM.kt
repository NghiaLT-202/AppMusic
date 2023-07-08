package com.example.appmusic.ui.main.home.video.videodetail.comment

import com.example.tfmmusic.common.Constant

@HiltViewModel
class BottomsheetCommentVM @Inject constructor(youtubeRepository: YoutubeRepository) :
    BaseViewModel() {
    var itemMutableLiveData: MutableLiveData<VideoYoutube> = MutableLiveData<VideoYoutube>()
    var youtubeRepository: YoutubeRepository

    init {
        this.youtubeRepository = youtubeRepository
    }

    fun getItemVideo(videoYoutube: VideoYoutube?) {
        youtubeRepository.getVideos(Constant.getAPIKey(), videoYoutube, false)
            .subscribe(object : SingleObserver<VideoYoutube?>() {
                fun onSubscribe(@NonNull d: Disposable?) {}
                fun onSuccess(@NonNull videoYoutube: VideoYoutube?) {
                    itemMutableLiveData.postValue(videoYoutube)
                }

                fun onError(@NonNull e: Throwable) {
                    getItemVideo(videoYoutube)
                    e.printStackTrace()
                }
            })
    }
}