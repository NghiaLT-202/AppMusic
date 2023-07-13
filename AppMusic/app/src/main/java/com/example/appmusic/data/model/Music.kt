package com.example.appmusic.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Music(
    var musicFile: String?,
    var musicName: String?,
    var nameSinger: String?,
    var nameAlbum: String?,
    var isCheckFavorite: Boolean,
    var namePlayList: String?,
    var date: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @Ignore
    var imageSong: Bitmap? = null

    override fun toString(): String {
        return "Music{" +
                "id=" + id +
                ", musicFile='" + musicFile + '\'' +
                ", musicName='" + musicName + '\'' +
                ", nameSinger='" + nameSinger + '\'' +
                ", nameAlbum='" + nameAlbum + '\'' +
                ", imageSong=" + imageSong +
                ", checkFavorite=" + isCheckFavorite +
                ", namePlayList='" + namePlayList + '\'' +
                '}'
    }
}