package com.example.appmusic.ui.main.home.playlistfragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.databinding.FragmentPlayListMusicBinding
import com.example.appmusic.ui.base.BaseBindingFragment

class PlaylistFragment : BaseBindingFragment<FragmentPlayListMusicBinding, PlaylistViewModel>() {
    private val listPlayList: List<PlayList> = ArrayList()
    private val listMusicPlaylist: List<Music> = ArrayList()

    //    private PlaylistAdapter playlistAdapter;
    //    private DialogAddPlayListFragment dialogAddPlayListFragment;
    override val layoutId: Int
        get() = R.layout.fragment_play_list_music

    override fun getViewModel(): Class<PlaylistViewModel> {
        return PlaylistViewModel::class.java
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {}
}