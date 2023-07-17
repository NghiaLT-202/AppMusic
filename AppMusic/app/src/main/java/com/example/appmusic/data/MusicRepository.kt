package com.example.appmusic.data

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import com.example.appmusic.data.model.Music
import timber.log.Timber
import javax.inject.Inject

class MusicRepository @Inject constructor() {
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
            while (cursor.moveToNext()) {
                val column = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)
                val duration = if (column >= 0) cursor.getLong(column) else -1

                if (duration > 0) {
                    val path =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA))
                    val album =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM))
                    val artist =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST))
                    val dateTime =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_ADDED))

                    try {
                        MediaMetadataRetriever().use { mmr ->
                            mmr.setDataSource(path)
                            val data = mmr.embeddedPicture
                            val name =
                                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE))
                            val music = Music().apply {
                                musicFile = path
                                musicName = name
                                nameSinger = artist
                                nameAlbum = album
                                imageSong = null
                                checkFavorite = false
                                namePlayList = null
                                date = dateTime
                            }
                            if (data != null) {
                                music.imageSong = BitmapFactory.decodeByteArray(data, 0, data.size)
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
        return musicList
    }
    fun getAllMusicFavourite(checkFavorite: Boolean): MutableList<Music> {
        val musicList = mutableListOf<Music>()

//        return database.musicDao().getAllFavouriteMusic(checkFavorite)
        return musicList
    }
}

