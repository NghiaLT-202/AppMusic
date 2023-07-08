package com.example.appmusic.data.model.table

import androidx.room.ColumnInfo

@Entity(tableName = "Recent")
class Recent(@field:ColumnInfo(name = "name") var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}