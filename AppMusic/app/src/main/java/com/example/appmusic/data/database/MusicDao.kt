package com.example.appmusic.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList

@Dao
interface MusicDao {
    //MUSIC


    @get:Query("SELECT * FROM music ")
    val allMusicDB: MutableList<Music>

    @get:Query("SELECT * FROM music ORDER BY namePlayList DESC ")
    val allMusicSortDB: MutableList<Music>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusic(music: Music)

    @Query("UPDATE  music  set namePlayList=:name where id=:id")
    fun updateNameMusic(name: String, id: Int)

    //PLAYLIST
    @Query("SELECT * FROM playlist")
    fun getAllPlayListMusic(): MutableList<PlayList>

    @Query("SELECT * FROM music where namePlayList=:name")
    fun getDetailPlaylist(name: String?): MutableList<Music>

    @Query("SELECT * FROM music where namePlayList=:namePlayListMusic")
    fun getAllMusicPlayList(namePlayListMusic: String): MutableList<Music>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayListMusic(playList: PlayList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusicOfPlayList(music: Music)

    @Query("DELETE FROM playlist")
    fun deletePlayListMusic()

    @Query("DELETE FROM playlist where namePlayList=:name")
    fun deletePlayListMusic(name: String)

    @Query("UPDATE  playlist  set namePlayList=:name where idPlayList=:id")
    fun updateNamePlayList(name: String, id: Int)

    
    //RECCENTLY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentMusic(itemRecent: ItemRecent)

    @Query("DELETE  From ItemRecent ")
    fun deleteRecentMusic()

    @Query("SELECT * FROM ItemRecent ")
    fun getAllRecentMusic(): MutableList<ItemRecent>

    //Favourite
    @Query("DELETE FROM music where musicFile=:path")
    fun deleteFavourite(path: String)
    @Query("SELECT * FROM music where checkFavorite=:check")
    fun getAllFavouriteMusic(check: Boolean): MutableList<Music>

}