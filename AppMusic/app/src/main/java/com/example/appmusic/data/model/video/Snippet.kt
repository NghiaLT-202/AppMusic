package com.example.appmusic.data.model.video

import com.example.tfmmusic.data.model.comment.SnippetComment

class Snippet {
    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null

    @SerializedName("channelId")
    @Expose
    var channelId: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("thumbnails")
    @Expose
    private var thumbnails: Thumbnails? = null

    @SerializedName("channelTitle")
    @Expose
    var channelTitle: String? = null

    @SerializedName("tags")
    @Expose
    var tags: List<String>? = null

    @SerializedName("categoryId")
    @Expose
    var categoryId: String? = null

    @SerializedName("liveBroadcastContent")
    @Expose
    var liveBroadcastContent: String? = null

    @SerializedName("localized")
    @Expose
    var localized: Localized? = null

    @SerializedName("snippet")
    @Expose
    private var snippet: SnippetComment? = null

    @SerializedName("topLevelComment")
    @Expose
    private var topLevelComment: TopLevelComment? = null
    override fun toString(): String {
        return "Snippet{" +
                "publishedAt='" + publishedAt + '\'' +
                ", channelId='" + channelId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnails=" + thumbnails +
                ", channelTitle='" + channelTitle + '\'' +
                ", tags=" + tags +
                ", categoryId='" + categoryId + '\'' +
                ", liveBroadcastContent='" + liveBroadcastContent + '\'' +
                ", localized=" + localized +
                ", snippet=" + snippet +
                ", topLevelComment=" + topLevelComment +
                '}'
    }

    fun getThumbnails(): Thumbnails? {
        return thumbnails
    }

    fun setThumbnails(thumbnails: Thumbnails?) {
        this.thumbnails = thumbnails
    }

    fun getSnippet(): SnippetComment? {
        return snippet
    }

    fun setSnippet(snippet: SnippetComment?) {
        this.snippet = snippet
    }

    fun getTopLevelComment(): TopLevelComment? {
        return topLevelComment
    }

    fun setTopLevelComment(topLevelComment: TopLevelComment?) {
        this.topLevelComment = topLevelComment
    }
}