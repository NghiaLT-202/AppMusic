package com.example.appmusic.data.model.video

import com.google.gson.annotations.Expose

class Statistics {
    @SerializedName("subscriberCount")
    @Expose
    var subscriberCount: String? = null

    @SerializedName("viewCount")
    @Expose
    var viewCount: String? = null

    @SerializedName("likeCount")
    @Expose
    var likeCount: String? = null

    @SerializedName("dislikeCount")
    @Expose
    var dislikeCount: String? = null

    @SerializedName("favoriteCount")
    @Expose
    var favoriteCount: String? = null

    @SerializedName("commentCount")
    @Expose
    var commentCount: String? = null
}