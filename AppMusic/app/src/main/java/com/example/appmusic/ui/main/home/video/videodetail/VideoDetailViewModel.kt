package com.example.appmusic.ui.main.home.video.videodetail

import com.example.tfmmusic.common.Constant

@HiltViewModel
class VideoDetailViewModel @Inject constructor(youtubeRepository: YoutubeRepository) :
    BaseViewModel() {
    var youtubeRepository: YoutubeRepository
    var videoTrendingLiveDataVN: MutableLiveData<VideoYoutube> = MutableLiveData<VideoYoutube>()
    var channelVideoDataVN: MutableLiveData<Channel> = MutableLiveData<Channel>()

    init {
        this.youtubeRepository = youtubeRepository
    }

    fun getVideoHome(query: String?) {
        youtubeRepository.getVideoSearch(Constant.getAPIKey(), query, 20)
            .subscribe(object : SingleObserver<VideoYoutube?>() {
                fun onSubscribe(@NonNull d: Disposable?) {
                    compositeDisposable.add(d)
                }

                fun onSuccess(@NonNull videoYoutube: VideoYoutube?) {
                    videoTrendingLiveDataVN.postValue(videoYoutube)
                }

                fun onError(@NonNull e: Throwable) {
                    getVideoHome(query)
                    e.printStackTrace()
                }
            })
    } //    public void getChanel(String id) {
    //        youtubeRepository.getChannel(id, Constant.API_KEY).subscribe(new SingleObserver<Channel>() {
    //            @Override
    //            public void onSubscribe(@NonNull Disposable d) {
    //
    //            }
    //
    //            @Override
    //            public void onSuccess(@NonNull Channel channel) {
    //                channelVideoDataVN.postValue(channel);
    //
    //            }
    //
    //            @Override
    //            public void onError(@NonNull Throwable e) {
    //
    //            }
    //        });
    //
    //
    //    }
}