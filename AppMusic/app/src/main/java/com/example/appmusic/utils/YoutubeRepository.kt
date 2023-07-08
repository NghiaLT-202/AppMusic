package com.example.appmusic.utils

import com.example.tfmmusic.common.Constant

class YoutubeRepository @Inject constructor(youtubeAPI: YoutubeApi, database: Database) {
    val youtubeAPI: YoutubeApi
    var database: Database

    init {
        this.youtubeAPI = youtubeAPI
        this.database = database
    }

    fun getChannel(id: String?, key: String?): Single<Channel> {
        return youtubeAPI.getChannel(id, key)
    }

    fun getVideoSearch(apiKey: String?, query: String?, maxResult: Int): Single<VideoYoutube> {
        return youtubeAPI.getVideoYoutube(
            apiKey,
            Constant.PART_SNIPPET,
            query,
            Constant.TYPE,
            maxResult
        )
    }

    private fun getVideo(
        apiKey: String,
        videoYoutube: VideoYoutube,
        isLoadMore: Boolean
    ): VideoYoutube {
        synchronized(videoYoutube) {
            val items: List<Items> = videoYoutube.getItems()
            var i = 0
            if (isLoadMore) {
                if (items.size >= 6) {
                    i = items.size - 6
                }
            }
            while (i < items.size) {
                getContentDetail(apiKey, items[i].getId().getVideoId(), videoYoutube, i)
                i++
            }
            return videoYoutube
        }
    }

    @Synchronized
    private fun getContentDetail(
        apiKey: String,
        videoId: String,
        videoYoutube: VideoYoutube,
        pos: Int
    ): VideoYoutube {
        youtubeAPI.getVideo(videoId, apiKey).subscribe(object : SingleObserver<ItemVideo?>() {
            fun onSubscribe(@NonNull d: Disposable?) {}
            fun onSuccess(@NonNull itemVideo: ItemVideo) {
                val contentDetails = ContentDetails()
                contentDetails.setDuration(
                    itemVideo.getItems().get(0).getContentDetails().getDuration()
                )
                videoYoutube.getItems().get(pos).setContentDetails(contentDetails)
            }

            fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
            }
        })
        return videoYoutube
    }

    fun getVideos(
        apiKey: String,
        videoYoutube: VideoYoutube,
        isLoadMore: Boolean
    ): Single<VideoYoutube> {
        return Single.fromCallable { getVideo(apiKey, videoYoutube, isLoadMore) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getComment(maxResult: Int, videoId: String?, key: String?): Single<Comment> {
        return youtubeAPI.getCommentYoutube(maxResult, videoId, key)
    }

    fun insertVideoRecent(videoRecent: VideoRecent?) {
        Completable.fromAction { database.videoDao().insertVideoReccent(videoRecent) }
            .subscribeOn(Schedulers.io()).subscribe()
    }

    val allRecent: Single<List<VideoRecent>>
        get() = Single.fromCallable { database.videoDao().getAllRecentMusic() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    val allHistorySearch: Single<List<HistorySearch>>
        get() = Single.fromCallable { database.videoDao().getAllHistorySearch() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    //Insert HistorySearch
    fun insertHistorySearch(historySearch: HistorySearch?) {
        Completable.fromAction { database.videoDao().insertHistorySearchVideo(historySearch) }
            .subscribeOn(Schedulers.io()).subscribe()
    }
}