package com.example.appmusic.data.database

import androidx.room.RoomDatabase

@Database(
    entities = [Music::class, PlayList::class, ItemRecent::class, VideoRecent::class, HistorySearch::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun musicDao(): MusicDao?
    abstract fun videoDao(): VideoDao?

    companion object {
        const val DATABASE_NAME = "TFM_Database_2"
        val MIGRATION_1: Migration = object : Migration(1, 2) {
            fun migrate(database: SupportSQLiteDatabase?) {
//            database.execSQL("ALTER TABLE RecentSearchKey"
//                    + " ADD COLUMN keyRecent TEXT NOT NULL DEFAULT 0 ");
            }
        }
    }
}