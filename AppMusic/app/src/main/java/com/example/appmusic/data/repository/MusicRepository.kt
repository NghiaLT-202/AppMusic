package com.example.appmusic.data.repository

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.example.appmusic.data.database.Database
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.tfmmusic.data.database.Database
import java.util.Collections

class MusicRepository @Inject constructor(database: Database) {
    var database: Database

    init {
        this.database = database
    }

    fun getMusicDevices(context: Context): List<Music?> {
        val musicList: MutableList<Music?> = ArrayList<Music?>()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf<String>(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.DURATION,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.DATE_ADDED
        )
        val cursor = context.contentResolver.query(
            uri,
            projection,
            MediaStore.Audio.AudioColumns.DATA + " like ? ",
            arrayOf<String>("%mp3"),
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
                            val data: ByteArray = mmr.getEmbeddedPicture()
                            val name =
                                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE))
                            val music = Music(path, name, artist, album, false, null, date)
                            if (data != null) {
                                music.setImageSong(
                                    BitmapFactory.decodeByteArray(
                                        data,
                                        0,
                                        data.size
                                    )
                                )
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

    fun getAllMusicFavourite(checkFavorite: Boolean): Single<List<Music>> {
        return Single.fromCallable { database.musicDao().getAllFavouriteMusic(checkFavorite) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMusicDevice(context: Context): Single<List<Music>> {
        return Single.fromCallable { getMusicDevices(context) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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

    val allPlayList: Single<List<PlayList>>
        get() = Single.fromCallable { database.musicDao().getAllPlayListMusic() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getAllMusicPlayList(namePlayList: String?): Single<List<Music>> {
        return Single.fromCallable { database.musicDao().getAllMusicPlayList(namePlayList) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    val allMusicSortDB: Single<List<Music>>
        get() = Single.fromCallable { database.musicDao().getAllMusicSortDB() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getAllDetailPlayListName(name: String?): Single<List<Music>> {
        return Single.fromCallable { database.musicDao().getDetailPlaylist(name) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertPlayList(playList: PlayList?) {
        Completable.fromAction { database.musicDao().insertPlayListMusic(playList) }
            .subscribeOn(Schedulers.io()).subscribe()
    }

    fun insertMusicOfPlayList(music: Music?) {
        Completable.fromAction { database.musicDao().insertMusicofPlayList(music) }
            .subscribeOn(Schedulers.io()).subscribe()
    }

    fun deletePlayList(name: String?) {
        Completable.fromAction { database.musicDao().deletePlayListMusic(name) }
            .subscribeOn(Schedulers.io()).subscribe()
    }

    fun updateNamePlayList(name: String?, id: Int) {
        Completable.fromAction { database.musicDao().UpdateNamePlayList(name, id) }
            .subscribeOn(Schedulers.io()).subscribe()
    }

    fun insertRecentMusic(itemRecent: ItemRecent?) {
        Completable.fromAction { database.musicDao().insertReccentMusic(itemRecent) }
            .subscribeOn(Schedulers.io()).subscribe()
    }

    val allRecentMusic: Single<List<ItemRecent>>
        get() = Single.fromCallable { database.musicDao().getAllReccentMusic() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteRecentMusic() {
        Completable.fromAction { database.musicDao().deleteReccentMusic() }
            .subscribeOn(Schedulers.io()).subscribe()
    }
}