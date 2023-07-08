package com.example.appmusic.di

import android.app.Application
import androidx.room.Room

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
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            Database.DATABASE_NAME
        )
            .addMigrations(Database.MIGRATION_1)
            .build()
    }

    @Provides
    @Singleton
    fun provideThreadDao(db: Database): MusicDao {
        return db.musicDao()
    }
}