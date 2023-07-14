package com.example.appmusic.data.repository

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import com.example.appmusic.data.database.Database
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.Collections
import java.util.concurrent.Callable

class MusicRepository  {
    lateinit var database: Database
    constructor(){
        this.database=database
    }

    fun getMusicDevices(context: Context?): MutableList<Music> {
        val musicList: MutableList<Music> = ArrayList()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.DURATION,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.DATE_ADDED
        )
        val cursor = context!!.contentResolver.query(
            uri,
            projection,
            MediaStore.Audio.AudioColumns.DATA + " like ? ",
            arrayOf("%mp3"),
            null
        )
        if (cursor != null) {
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
                    val date =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_ADDED))
                    try {
                        MediaMetadataRetriever().use { mmr ->
                            mmr.setDataSource(path)
                            val data = mmr.embeddedPicture
                            val name =
                                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE))
                            val music = Music(path, name, artist, album, false, null, date)
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
            cursor.close()
        }
        Collections.reverse(musicList)
        return musicList
    }

    fun getAllMusicFavourite(checkFavorite: Boolean): MutableList<Music> {
        return database.musicDao().getAllFavouriteMusic(checkFavorite)
    }

    fun getMusicDevice(context: Context?): MutableList<Music> {
        return getMusicDevices(context)
    }

    fun insert(music: Music?) {
        Completable.fromAction { database.musicDao().insertMusic(music) }
            .subscribeOn(Schedulers.io()).subscribe()
    }

    fun deleteFavourite(path: String?) {
        Completable.fromAction { database.musicDao().deleteFavourite(path) }
            .subscribeOn(Schedulers.io()).subscribe()
    }

    fun UpdateNameMusic(name: String?, id: Int) {
        Completable.fromAction { database.musicDao().UpdateNameMusic(name, id) }
            .subscribeOn(Schedulers.io()).subscribe()
    }

    fun getAllPlayList(): MutableList<PlayList> {
        return database.musicDao().getAllPlayListMusic()


    }

    fun getAllMusicPlayList(namePlayList: String): MutableList<Music> {
        return database.musicDao().getAllMusicPlayList(namePlayList)
    }

    fun getAllMusicSortDB(): MutableList<Music> {
        return database.musicDao().allMusicSortDB

    }

    fun getAllDetailPlayListName(name: String): MutableList<Music> {
        return database.musicDao().getDetailPlaylist(name)
    }

    fun insertPlayList(playList: PlayList?) {
        database.musicDao().insertPlayListMusic(playList)
    }

    fun insertMusicOfPlayList(music: Music?) {
       database.musicDao().insertMusicofPlayList(music)
    }

    fun deletePlayList(name: String) {
      database.musicDao().deletePlayListMusic(name)
    }

    fun updateNamePlayList(name: String, id: Int) {
       database.musicDao().UpdateNamePlayList(name, id)
    }

    fun insertRecentMusic(itemRecent: ItemRecent?) {
        database.musicDao().insertReccentMusic(itemRecent)
    }

    fun allRecentMusic(): MutableList<ItemRecent> {
        return database.musicDao().allReccentMusic as MutableList<ItemRecent>
    }


    fun deleteRecentMusic() {
       database.musicDao().deleteReccentMusic()
    }
}