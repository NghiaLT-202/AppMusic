package com.example.appmusic.data.model.video

import com.google.gson.annotations.Expose

class Localized {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null
}