package com.example.appmusic.ui.main.home.video.videosearch

import com.example.tfmmusic.common.Constant

@HiltViewModel
class VideoSearchViewmodel @Inject constructor(youtubeRepository: YoutubeRepository) :
    BaseViewModel() {
    var youtubeRepository: YoutubeRepository
    var videoSearchLiveDataVN: MutableLiveData<VideoYoutube> = MutableLiveData<VideoYoutube>()
    var historySearchLiveData: MutableLiveData<List<HistorySearch>> =
        MutableLiveData<List<HistorySearch>>()

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
                    videoSearchLiveDataVN.postValue(videoYoutube)
                }

                fun onError(@NonNull e: Throwable) {
                    getVideoHome(query)
                    e.printStackTrace()
                }
            })
    }

    val allHistorySearch: Unit
        get() {
            youtubeRepository.getAllHistorySearch()
                .subscribe(object : SingleObserver<List<HistorySearch?>?>() {
                    fun onSubscribe(@NonNull d: Disposable?) {
                        compositeDisposable.add(d)
                    }

                    fun onSuccess(@NonNull historySearches: List<HistorySearch?>?) {
                        historySearchLiveData.postValue(historySearches)
                    }

                    fun onError(@NonNull e: Throwable) {
                        e.printStackTrace()
                    }
                })
        }

    fun inserHistorySearch(historySearch: HistorySearch?) {
        youtubeRepository.insertHistorySearch(historySearch)
    }
}