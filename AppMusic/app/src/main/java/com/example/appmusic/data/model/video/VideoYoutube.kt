package com.example.appmusic.data.model.video

import com.google.gson.annotations.Expose

class VideoYoutube {
    @SerializedName("items")
    @Expose
    var items: List<Items?>? = null
    override fun toString(): String {
        return "VideoYoutube{" +
                "items=" + items +
                '}'
    }
}