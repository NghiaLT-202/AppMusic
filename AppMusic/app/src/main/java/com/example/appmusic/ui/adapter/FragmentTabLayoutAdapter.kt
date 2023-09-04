package com.example.appmusic.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appmusic.ui.main.home.albumfragment.AlbumFragment
import com.example.appmusic.ui.main.home.folderfragment.FolderFragment
import com.example.appmusic.ui.main.home.musicfragment.SongFragment
import com.example.appmusic.ui.main.home.singerfragment.SingerFragment

class FragmentTabLayoutAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val songFragment: SongFragment by lazy {  SongFragment()}
    private val singFragment: SingerFragment by lazy {  SingerFragment()}
    private val albumFragment: AlbumFragment by lazy {  AlbumFragment()}
    private val folderFragment: FolderFragment by lazy {  FolderFragment()}
    override fun createFragment(position: Int): Fragment {
        when (position) {


            1 -> return singFragment
            2 -> return albumFragment
            3 -> return folderFragment
        }
        return songFragment
    }

    override fun getItemCount(): Int {
        return 4
    }
}