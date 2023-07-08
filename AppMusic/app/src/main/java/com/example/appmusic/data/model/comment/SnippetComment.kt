package com.example.appmusic.data.model.comment

import com.google.gson.annotations.Expose

class SnippetComment {
    @SerializedName("videoId")
    @Expose
    var videoId: String? = null

    @SerializedName("textDisplay")
    @Expose
    var textDisplay: String? = null

    @SerializedName("textOriginal")
    @Expose
    var textOriginal: String? = null

    @SerializedName("authorDisplayName")
    @Expose
    var authorDisplayName: String? = null

    @SerializedName("authorProfileImageUrl")
    @Expose
    var authorProfileImageUrl: String? = null

    @SerializedName("authorChannelUrl")
    @Expose
    var authorChannelUrl: String? = null

    @SerializedName("authorChannelId")
    @Expose
    var authorChannelId: AuthorChannelId? = null

    @SerializedName("canRate")
    @Expose
    var canRate: Boolean? = null

    @SerializedName("viewerRating")
    @Expose
    var viewerRating: String? = null

    @SerializedName("likeCount")
    @Expose
    var likeCount: Int? = null

    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null
}