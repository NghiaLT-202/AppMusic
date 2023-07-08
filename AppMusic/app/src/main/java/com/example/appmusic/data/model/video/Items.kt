package com.example.appmusic.data.model.video

import com.google.gson.annotations.Expose
import java.nio.channels.Channel

class Items {
    @SerializedName("kind")
    @Expose
    var kind: String? = null

    @SerializedName("etag")
    @Expose
    var etag: String? = null

    @SerializedName("id")
    @Expose
    var id: Id? = null

    @SerializedName("snippet")
    @Expose
    var snippet: Snippet? = null

    @SerializedName("contentDetails")
    @Expose
    var contentDetails: ContentDetails? = null

    @SerializedName("statistics")
    @Expose
    var statistics: Statistics? = null
    var channel: Channel? = null
    override fun toString(): String {
        return "Items{" +
                "kind='" + kind + '\'' +
                ", etag='" + etag + '\'' +
                ", id=" + id +
                ", snippet=" + snippet +
                ", contentDetails=" + contentDetails +
                ", statistics=" + statistics +
                ", channel=" + channel +
                '}'
    }
}