package com.example.appmusic.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class ItemRecent {
    @PrimaryKey(autoGenerate = true)
    val id = 0
    val musicFile: String? = null
    val musicName: String? = null
    val nameSinger: String? = null
    val nameAlbum: String? = null

    @Ignore
     val imageSong: Bitmap? = null
     val namePlayList: String? = null
}