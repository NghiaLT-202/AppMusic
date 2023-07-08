package com.example.appmusic.data.model.video

import com.google.gson.annotations.Expose

class ContentDetails {
    @SerializedName("duration")
    @Expose
    var duration: String? = null

    @SerializedName("dimension")
    @Expose
    var dimension: String? = null

    @SerializedName("definition")
    @Expose
    var definition: String? = null

    @SerializedName("caption")
    @Expose
    var caption: String? = null

    @SerializedName("licensedContent")
    @Expose
    var licensedContent: Boolean? = null

    @SerializedName("contentRating")
    @Expose
    var contentRating: ContentRating? = null

    @SerializedName("projection")
    @Expose
    var projection: String? = null
    override fun toString(): String {
        return "ContentDetails{" +
                "duration='" + duration + '\'' +
                ", dimension='" + dimension + '\'' +
                ", definition='" + definition + '\'' +
                ", caption='" + caption + '\'' +
                ", licensedContent=" + licensedContent +
                ", contentRating=" + contentRating +
                ", projection='" + projection + '\'' +
                '}'
    }
}