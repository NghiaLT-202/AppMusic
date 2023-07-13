package com.example.appmusic.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlayList(
    @field:ColumnInfo var namePlayList: String,
    @field:ColumnInfo var pathPlayList: String?,
    @field:ColumnInfo var totalSong: Int
) {
    @PrimaryKey(autoGenerate = true)
    var idPlayList = 0

}