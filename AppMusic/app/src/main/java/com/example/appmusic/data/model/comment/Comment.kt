package com.example.appmusic.data.model.comment

import com.google.gson.annotations.Expose

class Comment {
    @SerializedName("kind")
    @Expose
    var kind: String? = null

    @SerializedName("etag")
    @Expose
    var etag: String? = null

    @SerializedName("nextPageToken")
    @Expose
    var nextPageToken: String? = null

    @SerializedName("items")
    @Expose
    var items: List<ItemComment>? = null
}