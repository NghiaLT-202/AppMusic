package com.example.appmusic.data.database

import androidx.room.Dao

@Dao
interface VideoDao {
    //RECCENT VIDEO
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideoReccent(videoRecent: VideoRecent?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistorySearchVideo(historySearch: HistorySearch?)

    @get:Query("SELECT * FROM videorecent")
    val allRecentMusic: List<Any?>?

    @get:Query("SELECT * FROM historySearch")
    val allHistorySearch: List<Any?>?
}