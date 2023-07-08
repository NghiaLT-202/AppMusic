package com.example.appmusic.data.api

import com.example.appmusic.data.model.ItemVideo
import com.example.appmusic.data.model.video.VideoYoutube


interface YoutubeApi {
    //
    @GET("commentThreads?part=snippet")
    fun getCommentYoutube(
        @Query("maxResults") maxResults: Int,
        @Query("videoId") videoId: String?,
        @Query("key") key: String?
    ): Single<Comment?>?

    @GET("channels?part=snippet&part=statistics")
    fun getChannel(
        @Query("id") id: String?,
        @Query("key") key: String?
    ): Single<Channel?>?

    @GET("search/")
    fun getVideoYoutube(
        @Query("key") key: String?,
        @Query("part") part_snippet: String?,
        @Query("q") search: String?,
        @Query("type") type: String?,
        @Query("maxResults") maxResult: Int
    ): Single<VideoYoutube?>?

    @GET("videos?part=snippet&part=contentDetails&part=statistics")
    fun getVideo(
        @Query("id") videoId: String?,
        @Query("key") key: String?
    ): Single<ItemVideo?>? //    @GET("channels?part=snippet&part=statistics")
    //    fun getChannelYoutube(
    //            @Query("id") channelId: String?,
    //            @Query("key") key: String?
    //    ): Call<ChannelYoutube?>?
}