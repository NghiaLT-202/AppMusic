package com.example.appmusic.data

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import com.example.appmusic.data.database.MusicDao
import com.example.appmusic.data.model.DataItemRecent
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.data.model.DataPlayList
import timber.log.Timber
import javax.inject.Inject

class MusicRepository @Inject constructor(var musicDao: MusicDao) {

    fun getMusicDevice(context: Context): MutableList<DataMusic> {
        return getMusicDevices(context)
    }

    private fun getMusicDevices(context: Context): MutableList<DataMusic> {
        val dataMusicList = mutableListOf<DataMusic>()

        val projection = arrayOf(
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATE_ADDED
        )

        val mmr = MediaMetadataRetriever() // Create one instance of MediaMetadataRetriever
        context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
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
                    try {
                        mmr.setDataSource(cursor.getString(columnData))
                        val dataMusic = DataMusic().apply {
                            musicFile = cursor.getString(columnData)
                            musicName = cursor.getString(columnTitle)
                            nameSinger = cursor.getString(columnArtist)
                            nameAlbum = cursor.getString(columnAlbum)
                            imageSong = null
                            checkFavorite = false
                            namePlayList = ""
                            date = cursor.getString(columnDateTime)
                            mmr.embeddedPicture?.let {
                                this.imageSong = BitmapFactory.decodeByteArray(it, 0, it.size)
                            }
                        }
                        dataMusicList.add(0, dataMusic) // Add items in reverse order directly
                    } catch (e: Exception) {
                        Timber.e(e)
                    }
                }
            }
        }
        mmr.release() // Release MediaMetadataRetriever
        return dataMusicList
    }

    fun insert(dataMusic: DataMusic) {
        musicDao.insertMusic(dataMusic)
    }

    fun deleteFavourite(path: String) {
        musicDao.deleteFavourite(path)
    }

    fun getAllMusicFavourite(checkFavorite: Boolean): MutableList<DataMusic> {
        return musicDao.getAllFavouriteMusic(checkFavorite)
    }

    fun deleteRecentMusic() {
        musicDao.deleteRecentMusic()
    }

    fun getAllRecentMusic(): MutableList<DataItemRecent> {
        return musicDao.getAllRecentMusic()
    }

    fun insertRecentMusic(dataItemRecent: DataItemRecent) {
        musicDao.insertRecentMusic(dataItemRecent)
    }

    fun getAllDetailPlayListName(name: String): MutableList<DataMusic> {
        return musicDao.getDetailPlaylist(name)
    }

    fun getAllMusicPlayList(namePlayList: String): MutableList<DataMusic> {
        return musicDao.getAllMusicPlayList(namePlayList)
    }


    fun insertPlayList(dataPlayList: DataPlayList) {
        musicDao.insertPlayListMusic(dataPlayList)
    }

    fun getAllPlayList(): MutableList<DataPlayList> {
        return musicDao.getAllPlayListMusic()
    }

    fun deletePlayList(name: String) {
        musicDao.deletePlayListMusic(name)
    }

    fun updateNamePlayList(name: String, id: Int) {
        musicDao.updateNamePlayList(name, id)
    }


    fun insertMusicOfPlayList(dataMusic: DataMusic) {
        musicDao.insertMusicOfPlayList(dataMusic)
    }
}

