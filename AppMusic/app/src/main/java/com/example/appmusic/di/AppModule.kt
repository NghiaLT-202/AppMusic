package com.example.appmusic.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.example.appmusic.data.database.Database
import com.example.appmusic.data.database.MusicDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun sharedPreference(context: Application?): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideRoomDb(context: Application): Database {
        return Room.databaseBuilder<Database>(
            context.applicationContext,
            Database::class.java,
            Database.Companion.DATABASE_NAME
        )
            .addMigrations(Database.Companion.MIGRATION_1)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideThreadDao(db: Database): MusicDao? {
        return db.musicDao()
    }
}