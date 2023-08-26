package com.example.appmusic.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmusic.data.model.DataItemRecent
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.data.model.DataPlayList

@Dao
interface MusicDao {
    //MUSIC


    @get:Query("SELECT * FROM dataMusic ")
    val allDataMusicDB: MutableList<DataMusic>

    @get:Query("SELECT * FROM dataMusic ORDER BY namePlayList DESC ")
    val allDataMusicSortDB: MutableList<DataMusic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusic(dataMusic: DataMusic)

    @Query("UPDATE  dataMusic  set namePlayList=:name where id=:id")
    fun updateNameMusic(name: String, id: Int)

    //PLAYLIST
    @Query("SELECT * FROM dataPlaylist")
    fun getAllPlayListMusic(): MutableList<DataPlayList>

    @Query("SELECT * FROM dataMusic where namePlayList=:name")
    fun getDetailPlaylist(name: String?): MutableList<DataMusic>

    @Query("SELECT * FROM dataMusic where namePlayList=:namePlayListMusic")
    fun getAllMusicPlayList(namePlayListMusic: String): MutableList<DataMusic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayListMusic(dataPlayList: DataPlayList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusicOfPlayList(dataMusic: DataMusic)

    @Query("DELETE FROM dataPlayList")
    fun deletePlayListMusic()

    @Query("DELETE FROM dataPlayList where namePlayList=:name")
    fun deletePlayListMusic(name: String)

    @Query("UPDATE  dataPlayList  set namePlayList=:name where idPlayList=:id")
    fun updateNamePlayList(name: String, id: Int)

    
    //RECCENTLY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentMusic(dataItemRecent: DataItemRecent)

    @Query("DELETE  From DataItemRecent ")
    fun deleteRecentMusic()

    @Query("SELECT * FROM DataItemRecent ")
    fun getAllRecentMusic(): MutableList<DataItemRecent>

    //Favourite
    @Query("DELETE FROM dataMusic where musicFile=:path")
    fun deleteFavourite(path: String)
    @Query("SELECT * FROM dataMusic where checkFavorite=:check")
    fun getAllFavouriteMusic(check: Boolean): MutableList<DataMusic>

}