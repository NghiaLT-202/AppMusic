package com.example.appmusic.data.model.recent

import com.google.gson.annotations.Expose

class Snippet {
    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null

    @SerializedName("channelId")
    @Expose
    var channelId: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("thumbnails")
    @Expose
    var thumbnails: Thumbnails? = null

    @SerializedName("channelTitle")
    @Expose
    var channelTitle: String? = null

    @SerializedName("liveBroadcastContent")
    @Expose
    var liveBroadcastContent: String? = null

    @SerializedName("publishTime")
    @Expose
    var publishTime: String? = null
}