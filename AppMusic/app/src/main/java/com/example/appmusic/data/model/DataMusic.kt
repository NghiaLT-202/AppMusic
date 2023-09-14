package com.example.appmusic.data.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class DataMusic(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        @ColumnInfo
        var uriMusic: String = "",
        @ColumnInfo
        var musicName: String = "",
        @ColumnInfo
        var nameSinger: String = "",
        @ColumnInfo
        var nameAlbum: String = "",
        @Ignore
        var imageSong: Bitmap? = null,
        @ColumnInfo
        var checkFavorite: Boolean = false,
        @ColumnInfo
        var namePlayList: String = "",
        @ColumnInfo
        var date: String = ""
)