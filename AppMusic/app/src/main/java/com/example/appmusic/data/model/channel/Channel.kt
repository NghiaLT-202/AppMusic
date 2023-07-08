package com.example.appmusic.data.model.channel

import com.google.gson.annotations.Expose

class Channel {
    @SerializedName("kind")
    @Expose
    var kind: String? = null

    @SerializedName("etag")
    @Expose
    var etag: String? = null

    @SerializedName("items")
    @Expose
    var items: List<ItemChannel>? = null
}