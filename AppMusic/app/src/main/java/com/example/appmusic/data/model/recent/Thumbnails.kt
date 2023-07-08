package com.example.appmusic.data.model.recent

import com.google.gson.annotations.Expose

class Thumbnails {
    @SerializedName("default")
    @Expose
    var default: Default? = null

    @SerializedName("medium")
    @Expose
    var medium: Medium? = null

    @SerializedName("high")
    @Expose
    var high: High? = null
    override fun toString(): String {
        return "Thumbnails{" +
                "_default=" + default +
                ", medium=" + medium +
                ", high=" + high +
                '}'
    }
}