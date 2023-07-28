package com.example.appmusic.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList

@Database(
    entities = [Music::class, PlayList::class, ItemRecent::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun musicDao(): MusicDao
    companion object {
        const val DATABASE_NAME = "TFM_Database_2"
        val MIGRATION_1: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE RecentSearchKey"
//                    + " ADD COLUMN keyRecent TEXT NOT NULL DEFAULT 0 ");
            }
        }
    }
}