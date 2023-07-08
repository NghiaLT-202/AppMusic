package com.example.appmusic.data.model.video

import androidx.room.Entity

@Entity
class HistorySearch(var textSeacrch: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}