package com.example.appmusic.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlayList {
    @PrimaryKey(autoGenerate = true)
    private val idPlayList = 0

    @ColumnInfo
    private val namePlayList: String? = null

    @ColumnInfo
    private val pathPlayList: String? = null

    @ColumnInfo
    private val totalSong = 0
}