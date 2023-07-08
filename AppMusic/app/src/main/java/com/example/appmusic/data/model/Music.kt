package com.example.appmusic.data.model

import android.graphics.Bitmap

@Entity
class Music {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var musicFile: String? = null
    var musicName: String? = null
    var nameSinger: String? = null
    var nameAlbum: String? = null

    @Ignore
    var imageSong: Bitmap? = null
    var isCheckFavorite = false
    var namePlayList: String? = null
    var date: String? = null



}
