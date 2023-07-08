package com.example.appmusic.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tfmmusic.ui.main.home.mymusic.MyMusicFragment

class MenuBottomAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MyMusicFragment()
            1 -> return VideoFragment()
        }
        return VideoFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}