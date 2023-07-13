package com.example.appmusic.data.model

import androidx.room.PrimaryKey

class Favourite(
    var musicFile: String,
    var musicName: String,
    var nameSinger: String,
    var nameAlbum: String,
    var imageSong: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}