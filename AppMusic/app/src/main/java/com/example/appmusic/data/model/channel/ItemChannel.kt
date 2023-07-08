package com.example.appmusic.data.model.channel

import com.google.gson.annotations.Expose

class ItemChannel {
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
    var snippet: SnippetChannel? = null

    @SerializedName("statistics")
    @Expose
    var statistics: StatisticsChannel? = null
}