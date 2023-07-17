package com.example.appmusic.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Music {
    @PrimaryKey(autoGenerate = true)
    val id = 0
    var musicFile: String? = null
    var musicName: String? = null
    var nameSinger: String? = null
    var nameAlbum: String? = null

    @Ignore
    var imageSong: Bitmap? = null
    var checkFavorite = false
    var namePlayList: String? = null
    var date: String? = null
}