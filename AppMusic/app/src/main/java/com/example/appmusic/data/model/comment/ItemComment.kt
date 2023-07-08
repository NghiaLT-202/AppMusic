package com.example.appmusic.data.model.comment

import com.example.tfmmusic.data.model.video.ContentDetails
import java.nio.channels.Channel

class ItemComment {
    @SerializedName("kind")
    @Expose
    var kind: String? = null

    @SerializedName("etag")
    @Expose
    var etag: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("snippet")
    @Expose
    private var snippet: Snippet? = null

    @SerializedName("contentDetails")
    @Expose
    private var contentDetails: ContentDetails? = null

    @SerializedName("statistics")
    @Expose
    private var statistics: Statistics? = null
    var channel: Channel? = null
    fun getSnippet(): Snippet? {
        return snippet
    }

    fun setSnippet(snippet: Snippet?) {
        this.snippet = snippet
    }

    fun getContentDetails(): ContentDetails? {
        return contentDetails
    }

    fun setContentDetails(contentDetails: ContentDetails?) {
        this.contentDetails = contentDetails
    }

    fun getStatistics(): Statistics? {
        return statistics
    }

    fun setStatistics(statistics: Statistics?) {
        this.statistics = statistics
    }

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