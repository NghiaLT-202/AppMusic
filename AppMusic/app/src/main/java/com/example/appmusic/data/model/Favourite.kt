package com.example.appmusic.data.model

import androidx.room.PrimaryKey

class Favourite {
    @PrimaryKey(autoGenerate = true)
    private val id = 0
    private val musicFile: String? = null
    private val musicName: String? = null
    private val nameSinger: String? = null
    private val nameAlbum: String? = null
    private val imageSong = 0
}