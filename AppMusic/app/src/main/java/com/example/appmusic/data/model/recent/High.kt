package com.example.appmusic.data.model.recent

import com.google.gson.annotations.Expose

class High {
    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("width")
    @Expose
    var width: Int? = null

    @SerializedName("height")
    @Expose
    var height: Int? = null
}