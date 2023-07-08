package com.example.appmusic.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class ItemRecent {
    @PrimaryKey(autoGenerate = true)
    private val id = 0
    private val musicFile: String? = null
    private val musicName: String? = null
    private val nameSinger: String? = null
    private val nameAlbum: String? = null

    @Ignore
    private val imageSong: Bitmap? = null
    private val namePlayList: String? = null
}