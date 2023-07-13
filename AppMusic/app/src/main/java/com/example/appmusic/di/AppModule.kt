package com.example.appmusic.di

import android.app.Applicationimport

android.content.SharedPreferencesimport android.preference.PreferenceManagerimport androidx.room.Roomimport com.example.appmusic.data .database.Databaseimport com.example.appmusic.data .database.MusicDaoimport dagger.Moduleimport dagger.Providesimport dagger.hilt.InstallInimport dagger.hilt.components.SingletonComponentimport javax.inject.Singleton
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
            .build()
    }

    @Provides
    @Singleton
    fun provideThreadDao(db: Database): MusicDao? {
        return db.musicDao()
    }
}