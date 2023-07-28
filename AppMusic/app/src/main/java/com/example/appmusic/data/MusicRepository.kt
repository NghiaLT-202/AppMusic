package com.example.appmusic.data

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import com.example.appmusic.data.database.Database
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import timber.log.Timber
import javax.inject.Inject

class MusicRepository @Inject constructor(var database: Database) {

    fun getMusicDevice(context: Context): MutableList<Music> {
        return getMusicDevices(context)
    }

    private fun getMusicDevices(context: Context): MutableList<Music> {
        val musicList = mutableListOf<Music>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.DURATION,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.DATE_ADDED
        )
        context.contentResolver.query(
            uri,
            projection,
            "${MediaStore.Audio.AudioColumns.DATA} like ?",
            arrayOf("%mp3"),
            null
        )?.use { cursor ->
            val columnDuration = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)
            val columnData = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)
            val columnAlbum = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)
            val columnArtist = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)
            val columnDateTime = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATE_ADDED)
            val columnTitle = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)
            while (cursor.moveToNext()) {
                val duration = if (columnDuration >= 0) cursor.getLong(columnDuration) else -1
                if (duration > 0) {
                    val path = cursor.getString(columnData)
                    val album = cursor.getString(columnAlbum)
                    val artist =
                        cursor.getString(columnArtist)
                    val dateTime =
                        cursor.getString(columnDateTime)
                    try {
                        MediaMetadataRetriever().use { mmr ->
                            mmr.setDataSource(path)
                            val data = mmr.embeddedPicture
                            val name = cursor.getString(columnTitle)
                            val music = Music().apply {
                                musicFile = path
                                musicName = name
                                nameSinger = artist
                                nameAlbum = album
                                imageSong = null
                                checkFavorite = false
                                namePlayList = ""
                                date = dateTime
                            }
                            data?.let {
                                music.imageSong = BitmapFactory.decodeByteArray(it, 0, it.size)

                            }
                            musicList.add(music)
                        }
                    } catch (e: Exception) {
                        Timber.e(e)
                    }
                }
            }
        }
        musicList.reverse()
        Timber.e("tunglt: musicList: " + musicList.size)
        return musicList
    }

    fun insert(music: Music) {
        Timber.e("ltnghia"+database.toString())
        database.musicDao().insertMusic(music)

    }

    fun deleteFavourite(path: String) {
        database.musicDao().deleteFavourite(path)
    }
    fun getAllMusicFavourite(checkFavorite: Boolean): MutableList<Music> {
      return database.musicDao().getAllFavouriteMusic(checkFavorite)
    }
    fun deleteRecentMusic() {
        database.musicDao().deleteReccentMusic()
    }
    fun getAllRecentMusic(): MutableList<ItemRecent> {
        return database.musicDao().getAllReccentMusic()
    }
    fun insertRecentMusic(itemRecent: ItemRecent) {
        database.musicDao().insertReccentMusic(itemRecent)
    }
}

