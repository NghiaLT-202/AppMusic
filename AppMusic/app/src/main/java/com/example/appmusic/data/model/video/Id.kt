package com.example.appmusic.data.model.video

import com.google.gson.annotations.Expose

class Id(
    @field:Expose @field:SerializedName("kind") var kind: String,
    @field:Expose @field:SerializedName(
        "videoId"
    ) var videoId: String
)