package com.example.appmusic.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.albumfragment.AlbumFragment
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.folderfragment.FolderFragment
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.MusicFragment
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.singerfragment.SingerFragment

class FragmentTabLayoutAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var musicFragment: MusicFragment? = null
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                if (musicFragment == null) musicFragment = MusicFragment()
                return musicFragment!!
            }

            1 -> return SingerFragment()
            2 -> return AlbumFragment()
            3 -> return FolderFragment()
        }
        return MusicFragment()
    }

    override fun getItemCount(): Int {
        return 4
    }
}