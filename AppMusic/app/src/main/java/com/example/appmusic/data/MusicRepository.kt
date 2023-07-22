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
            val columnDuration = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)
            val columnData = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)
            val columnAlbum = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)
            while (cursor.moveToNext()) {
                val duration = if (columnDuration >= 0) cursor.getLong(columnDuration) else -1
                if (duration > 0) {
                    val path = cursor.getString(columnData)
                    val album = cursor.getString(columnAlbum)
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

    fun getAllMusicFavourite(): MutableList<Music> {
        val musicList = mutableListOf<Music>()
        return musicList
    }
}

