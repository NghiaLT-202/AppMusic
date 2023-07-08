package com.example.appmusic.data.model.channel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StatisticsChannel {
    @SerializedName("viewCount")
    @Expose
    var viewCount: String? = null

    @SerializedName("subscriberCount")
    @Expose
    var subscriberCount: String? = null

    @SerializedName("hiddenSubscriberCount")
    @Expose
    var hiddenSubscriberCount: Boolean? = null

    @SerializedName("videoCount")
    @Expose
    var videoCount: String? = null
}