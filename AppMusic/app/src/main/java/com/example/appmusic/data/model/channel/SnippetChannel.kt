package com.example.appmusic.data.model.channel

import com.example.appmusic.data.model.video.Localized
import com.example.tfmmusic.data.model.recent.Thumbnails
import com.example.tfmmusic.data.model.video.Localized
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SnippetChannel {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("customUrl")
    @Expose
    var customUrl: String? = null

    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null

    @SerializedName("defaultLanguage")
    @Expose
    var defaultLanguage: String? = null

    @SerializedName("localized")
    @Expose
    var localized: Localized? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("thumbnails")
    @Expose
    var thumbnails: Thumbnails? = null
}