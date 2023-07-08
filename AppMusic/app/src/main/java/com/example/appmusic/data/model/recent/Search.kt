package com.example.appmusic.data.model.recent

import com.google.gson.annotations.Expose

class Search {
    @SerializedName("kind")
    @Expose
    var kind: String? = null

    @SerializedName("etag")
    @Expose
    var etag: String? = null

    @SerializedName("nextPageToken")
    @Expose
    var nextPageToken: String? = null

    @SerializedName("regionCode")
    @Expose
    var regionCode: String? = null

    @SerializedName("pageInfo")
    @Expose
    var pageInfo: PageInfo? = null

    @SerializedName("items")
    @Expose
    var items: List<Item>? = null
}