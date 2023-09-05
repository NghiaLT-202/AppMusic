package com.example.appmusic.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataPlayList(
        @PrimaryKey(autoGenerate = true)
        var idPlayList: Int = 0,
        @ColumnInfo
        var namePlayList: String = "",
        @ColumnInfo
        var pathPlayList: String = "",
        @ColumnInfo
        var totalSong: Int = 0
)