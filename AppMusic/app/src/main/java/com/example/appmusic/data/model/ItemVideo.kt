package com.example.appmusic.data.model

import com.google.gson.annotations.Expose

class ItemVideo {
    @SerializedName("kind")
    @Expose
    private val kind: String? = null

    @SerializedName("etag")
    @Expose
    private val etag: String? = null

    @SerializedName("items")
    @Expose
    private val items: List<DetailVideo>? = null
}