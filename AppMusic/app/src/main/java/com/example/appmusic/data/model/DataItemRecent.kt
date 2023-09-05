package com.example.appmusic.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class DataItemRecent(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var musicFile: String = "",
        var musicName: String = "",
        var nameSinger: String = "",
        var nameAlbum: String = "",
        @Ignore
        var imageSong: Bitmap? = null,
        var namePlayList: String = ""
)