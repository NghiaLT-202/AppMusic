package com.example.appmusic.data.model.recent

import com.google.gson.annotations.Expose

class Id {
    @SerializedName("kind")
    @Expose
    var kind: String? = null

    @SerializedName("videoId")
    @Expose
    var videoId: String? = null
}