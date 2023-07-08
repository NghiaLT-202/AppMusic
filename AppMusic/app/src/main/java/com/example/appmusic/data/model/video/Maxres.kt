package com.example.appmusic.data.model.video

import com.google.gson.annotations.Expose

class Maxres {
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