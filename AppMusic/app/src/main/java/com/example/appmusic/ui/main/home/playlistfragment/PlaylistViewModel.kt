package com.example.appmusic.ui.main.home.playlistfragment

import androidx.lifecycle.MutableLiveData
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel //    public  void  getAllPlayList(){
@Inject constructor(private val musicRepository: MusicRepository) : BaseViewModel() {
    var listPlayList = MutableLiveData<List<PlayList>>()
    var listMusicPlaylist = MutableLiveData<List<Music>>()
    //        listPlayList.postValue(musicRepository.getAllPlayList());
    //
    //       
    //    }
    //    public void insertPlayList(PlayList playList){
    //        musicRepository.insertPlayList(playList);
    //    }
    //    public void deletePlayList(String name){
    //        musicRepository.deletePlayList(name);
    //    }
    //    public void updateNamePlayList(String name, int id){
    //        musicRepository.updateNamePlayList(name,id);
    //    }
    //    public void getAllMusicPlayList(String namePlayList) {
    //        listMusicPlaylist.postValue(  musicRepository.getAllMusicPlayList(namePlayList));
    //
    //    }
}