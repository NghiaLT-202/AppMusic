package com.example.appmusic.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tfmmusic.ui.main.home.mymusic.mymusicdetail.albumfragment.AlbumFragment

class FragmentTabLayoutAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var musicFragment: MusicFragment? = null
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                if (musicFragment == null) musicFragment = MusicFragment()
                return musicFragment
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