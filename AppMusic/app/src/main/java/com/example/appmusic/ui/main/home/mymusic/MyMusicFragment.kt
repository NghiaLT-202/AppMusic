package com.example.appmusic.ui.main.home.mymusic

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import com.example.appmusic.R
import com.example.appmusic.databinding.FragmentMymusicBinding
import com.example.appmusic.ui.adapter.FragmentTabLayoutAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyMusicFragment : BaseBindingFragment<FragmentMymusicBinding?, MyMusicViewmodel>() {


    override fun getViewModel(): Class<MyMusicViewmodel>? {
        return MyMusicViewmodel::class.java
    }

    override fun getLayoutId(): Int {
        return  R.layout.fragment_mymusic
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
    }

    private fun initListener() {
        binding!!.tvSearch.setOnClickListener { v: View? ->
            (requireActivity() as MainActivity).navController!!.navigate(
                R.id.fragment_research
            )
        }
        binding!!.imMenu.setOnClickListener { v: View? ->
            binding!!.drawerLayout.openDrawer(
                GravityCompat.START
            )
        }
        binding!!.navView.setNavigationItemSelectedListener { item: MenuItem ->
            item.isChecked = true
            binding!!.drawerLayout.closeDrawers()
            true
        }
        binding!!.viewFavourite.setOnClickListener { v: View? ->
            (requireActivity() as MainActivity).navController!!.navigate(
                R.id.fragment_favorite
            )
        }
        binding!!.viewListPlay.setOnClickListener { v: View? ->
            (requireActivity() as MainActivity).navController!!.navigate(
                R.id.fragment_list_music
            )
        }
        binding!!.viewRecent.setOnClickListener { v: View? ->
            (requireActivity() as MainActivity).navController!!.navigate(
                R.id.fragment_recently
            )
        }
    }

    private fun initAdapter() {
        val list = ArrayList<String>()
        list.add(getString(R.string.song))
        list.add(getString(R.string.singer))
        list.add(getString(R.string.album))
        list.add(getString(R.string.folder))
        val adapter = FragmentTabLayoutAdapter(childFragmentManager, lifecycle)
        binding!!.tabLayout.setTabTextColors(
            Color.parseColor("#80000000"),
            Color.parseColor("#000000")
        )
        binding!!.viewpager2.adapter = adapter
        TabLayoutMediator(
            binding!!.tabLayout,
            binding!!.viewpager2
        ) { tab: TabLayout.Tab, position: Int -> tab.text = list[position] }.attach()
    }
}