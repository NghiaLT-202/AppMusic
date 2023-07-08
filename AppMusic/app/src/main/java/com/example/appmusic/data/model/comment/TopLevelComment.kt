package com.example.appmusic.data.model.comment

import com.google.gson.annotations.Expose

class TopLevelComment {
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
    var snippet: SnippetComment? = null
}