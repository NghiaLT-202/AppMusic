package com.example.appmusic.data.model.recent

import com.google.gson.annotations.Expose
import java.nio.channels.Channel

class Item {
    @SerializedName("kind")
    @Expose
    var kind: String? = null

    @SerializedName("etag")
    @Expose
    var etag: String? = null

    @SerializedName("id")
    @Expose
    var id: Id? = null

    @SerializedName("snippet")
    @Expose
    var snippet: Snippet? = null

    @SerializedName("channel")
    @Expose
    var channel: Channel? = null
}