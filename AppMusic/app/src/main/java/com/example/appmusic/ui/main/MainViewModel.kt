package com.example.appmusic.ui.main

import android.content.Context
import com.example.tfmmusic.common.CommonEvent

@HiltViewModel
class MainViewModel @Inject constructor(
    musicRepository: MusicRepository,
    youtubeRepository: YoutubeRepository
) : BaseViewModel() {
    var itemPlayVideoMutableLiveData: MutableLiveData<VideoRecent> = MutableLiveData<VideoRecent>()
    var isStartMedia: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var listAllMusicDevice: MutableLiveData<List<Music>> = MutableLiveData<List<Music>>()
    var youtubeRepository: YoutubeRepository
    var videoTrendingLiveDataVN: MutableLiveData<VideoYoutube> = MutableLiveData<VideoYoutube>()
    var videoTrendingLiveDataVNLoadMore: MutableLiveData<VideoYoutube> =
        MutableLiveData<VideoYoutube>()
    var commentMutableLiveData: MutableLiveData<Comment> = MutableLiveData<Comment>()
    var event: LiveEvent<CommonEvent> = LiveEvent()
    var liveEvent: LiveEvent<MessageEvent> = LiveEvent()
    var listRecentLiveData: MutableLiveData<List<ItemRecent>> = MutableLiveData<List<ItemRecent>>()
    var musicRepository: MusicRepository

    init {
        this.musicRepository = musicRepository
        this.youtubeRepository = youtubeRepository
    }

    fun getAllMusicDetail(context: Context?) {
        if (listAllMusicDevice.getValue() != null) {
            if (listAllMusicDevice.getValue().size > 0) return
        }
        musicRepository.getMusicDevice(context).subscribe(object : SingleObserver<List<Music?>?>() {
            fun onSubscribe(@NonNull d: Disposable?) {
                compositeDisposable.add(d)
            }

            fun onSuccess(@NonNull list: List<Music?>?) {
                listAllMusicDevice.postValue(list)
            }

            fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
            }
        })
    }

    fun insertReccentMusic(itemRecent: ItemRecent?) {
        musicRepository.insertRecentMusic(itemRecent)
    }

    //    public void insertReccentVideo(VideoYoutube videoYoutube) {
    //        musicRepository.inserReccentVideo(videoYoutube);
    //
    //    }
    fun getVideoHome(query: String?) {
        youtubeRepository.getVideoSearch(Constant.getAPIKey(), query, 5)
            .subscribe(object : SingleObserver<VideoYoutube?>() {
                fun onSubscribe(@NonNull d: Disposable?) {
                    compositeDisposable.add(d)
                }

                fun onSuccess(@NonNull videoYoutube: VideoYoutube?) {
                    getVideo(videoYoutube, false)
                }

                fun onError(@NonNull e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    val allReccentMusic: Unit
        get() {
            musicRepository.getAllRecentMusic()
                .subscribe(object : SingleObserver<List<ItemRecent?>?>() {
                    fun onSubscribe(@NonNull d: Disposable?) {
                        compositeDisposable.add(d)
                    }

                    fun onSuccess(@NonNull reccentlies: List<ItemRecent?>?) {
                        listRecentLiveData.postValue(reccentlies)
                    }

                    fun onError(@NonNull e: Throwable) {
                        e.printStackTrace()
                    }
                })
        }

    fun getVideoHomeLoadMore(query: String?, maxResult: Int) {
        youtubeRepository.getVideoSearch(Constant.getAPIKey(), query, maxResult)
            .subscribe(object : SingleObserver<VideoYoutube?>() {
                fun onSubscribe(@NonNull d: Disposable?) {
                    compositeDisposable.add(d)
                }

                fun onSuccess(@NonNull videoYoutube: VideoYoutube?) {
                    Timber.e("nghialt: getVideoHomeLoadMore")
                    getVideo(videoYoutube, true)
                }

                fun onError(@NonNull e: Throwable) {
                    getVideoHomeLoadMore(query, maxResult)
                    e.printStackTrace()
                }
            })
    }

    fun getVideo(videoYoutube: VideoYoutube?, isLoadMore: Boolean) {
        youtubeRepository.getVideos(Constant.getAPIKey(), videoYoutube, isLoadMore)
            .subscribe(object : SingleObserver<VideoYoutube?>() {
                fun onSubscribe(@NonNull d: Disposable?) {
                    compositeDisposable.add(d)
                }

                fun onSuccess(@NonNull videoYoutube: VideoYoutube?) {
                    videoTrendingLiveDataVNLoadMore.postValue(videoYoutube)
                }

                fun onError(@NonNull e: Throwable) {
                    getVideo(videoYoutube, isLoadMore)
                    e.printStackTrace()
                }
            })
    }

    fun getComment(videoId: String?) {
        youtubeRepository.getComment(20, videoId, Constant.getAPIKey())
            .subscribe(object : SingleObserver<Comment?>() {
                fun onSubscribe(@NonNull d: Disposable?) {
                    compositeDisposable.add(d)
                }

                fun onSuccess(@NonNull comment: Comment?) {
                    commentMutableLiveData.postValue(comment)
                }

                fun onError(@NonNull e: Throwable) {
                    getComment(videoId)
                    e.printStackTrace()
                }
            })
    }
}