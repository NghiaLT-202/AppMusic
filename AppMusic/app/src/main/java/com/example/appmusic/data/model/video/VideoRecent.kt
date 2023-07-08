package com.example.appmusic.data.model.video

import androidx.room.ColumnInfo
import java.util.LinkedList

@Entity
class VideoRecent {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo
    var videoId: String? = null

    @ColumnInfo
    var title: String? = null

    @ColumnInfo
    var channelTitle: String? = null

    @ColumnInfo
    var duration: String? = null

    @ColumnInfo
    var url: String? = null

    @ColumnInfo
    var authorImageUrl: String? = null

    @ColumnInfo
    var description: String? = null

    @ColumnInfo
    var contentComent: String? = null

    constructor() {}
    constructor(
        videoId: String?,
        title: String?,
        channelTitle: String?,
        duration: String?,
        url: String?,
        authorImageUrl: String?,
        contentComent: String?,
        description: String?
    ) {
        this.videoId = videoId
        this.title = title
        this.channelTitle = channelTitle
        this.duration = duration
        this.url = url
        this.authorImageUrl = authorImageUrl
        this.contentComent = contentComent
        this.description = description
    }

    override fun toString(): String {
        return "VideoRecent{" +
                "id=" + id +
                ", videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", channelTitle='" + channelTitle + '\'' +
                ", duration='" + duration + '\'' +
                ", url='" + url + '\'' +
                '}'
    }

    companion object {
        fun getVideoRecent(videoYoutube: VideoYoutube, position: Int): VideoRecent {
            val itemPlayVideo = VideoRecent()
            itemPlayVideo.videoId = videoYoutube.items[position].id.videoId
            itemPlayVideo.title = videoYoutube.items[position].snippet.title
            itemPlayVideo.channelTitle = videoYoutube.items[position].snippet.channelTitle
            itemPlayVideo.description = videoYoutube.items[position].snippet.description
            itemPlayVideo.url = videoYoutube.items[position].snippet.thumbnails.getHigh().getUrl()
            return itemPlayVideo
        }

        fun getListVideoRecent(videoYoutube: VideoYoutube): List<VideoRecent> {
            val recentList: MutableList<VideoRecent> = LinkedList()
            for (i in videoYoutube.items.indices) {
                val videoRecent = getVideoRecent(videoYoutube, i)
                recentList.add(videoRecent)
            }
            return recentList
        }
    }
}