package com.example.appmusic.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class ItemPlayVideo {
    @PrimaryKey(autoGenerate = true)
    private val id = 0
    private val videoId: String? = null
    private val titleVideo: String? = null
    private val channelTitle: String? = null
    private val urlPreViewImage: String? = null
    private val description: String? = null

    @ColumnInfo
    private val duration: String? = null
}