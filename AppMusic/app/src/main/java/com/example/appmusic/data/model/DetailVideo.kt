package com.example.appmusic.data.model

import com.example.appmusic.data.model.video.ContentDetails
import com.example.tfmmusic.data.model.video.ContentDetails
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DetailVideo {
    @SerializedName("kind")
    @Expose
    private val kind: String? = null

    @SerializedName("etag")
    @Expose
    private val etag: String? = null

    @SerializedName("id")
    @Expose
    private val id: String? = null

    @SerializedName("contentDetails")
    @Expose
    private val contentDetails: ContentDetails? = null
}