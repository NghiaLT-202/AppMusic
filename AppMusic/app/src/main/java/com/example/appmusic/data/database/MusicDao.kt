package com.example.appmusic.data.database

import androidx.room.Dao

@Dao
interface MusicDao {
    //MUSIC
    @Query("SELECT * FROM music where checkFavorite=:check")
    fun getAllFavouriteMusic(check: Boolean): List<Music?>?

    @get:Query("SELECT * FROM music ")
    val allMusicDB: List<Any?>?

    @get:Query("SELECT * FROM music ORDER BY namePlayList DESC ")
    val allMusicSortDB: List<Any?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusic(music: Music?)

    @Query("DELETE FROM music where musicFile=:path")
    fun deleteFavourite(path: String?)

    @Query("UPDATE  music  set namePlayList=:name where id=:id")
    fun UpdateNameMusic(name: String?, id: Int)

    //GET MUSIC PLAYLIST
    @Query("SELECT * FROM music where namePlayList=:namePlayListMusic")
    fun getAllMusicPlayList(namePlayListMusic: String?): List<Music?>?

    //PLAYLIST
    @get:Query("SELECT * FROM playlist")
    val allPlayListMusic: List<Any?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayListMusic(playList: PlayList?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusicofPlayList(music: Music?)

    @Query("DELETE FROM playlist")
    fun deletePlayListMusic()

    @Query("DELETE FROM playlist where namePlayList=:name")
    fun deletePlayListMusic(name: String?)

    @Query("UPDATE  playlist  set namePlayList=:name where idPlayList=:id")
    fun UpdateNamePlayList(name: String?, id: Int)

    //RECCENTLY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReccentMusic(itemRecent: ItemRecent?)

    @get:Query("SELECT * FROM ItemRecent ")
    val allReccentMusic: List<Any?>?

    @Query("SELECT * FROM music where namePlayList=:name")
    fun getDetailPlaylist(name: String?): List<Music?>?

    @Query("DELETE  From ItemRecent ")
    fun deleteReccentMusic()
}