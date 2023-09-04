package com.example.appmusic.ui.main.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import com.example.appmusic.R
import com.example.appmusic.databinding.FragmentHomeBinding
import com.example.appmusic.ui.adapter.FragmentTabLayoutAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseBindingFragment<FragmentHomeBinding, HomeViewModel>() {


    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_home


    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        mainViewModel.getAllMusicDetail(requireContext())

    }

    private fun initAdapter() {
        val list = listOf(
                getString(R.string.song),
                getString(R.string.singer),
                getString(R.string.album),
                getString(R.string.folder)
        )

        val adapter = FragmentTabLayoutAdapter(
                childFragmentManager,
                lifecycle
        )

        binding.tabLayout.setTabTextColors(
                Color.parseColor("#80000000"),
                Color.parseColor("#000000")
        )

        binding.viewpager2.adapter = adapter

        TabLayoutMediator(
                binding.tabLayout, binding.viewpager2
        ) { tab, position ->
            tab.text = list[position]
        }.attach()


    }


    private fun initListener() {
        binding.tvSearch.setOnClickListener {
            navigateFragment(R.id.fragment_research)
        }

        binding.imMenu.setOnClickListener { binding.drawerLayout.openDrawer(GravityCompat.START) }
        binding.navView.setNavigationItemSelectedListener { item ->
            item.isChecked = true
            binding.drawerLayout.closeDrawers()
            true
        }
        binding.viewFavourite.setOnClickListener {
            navigateFragment(R.id.fragment_favorite)
        }
        binding.viewListPlay.setOnClickListener {
            navigateFragment(R.id.fragment_list_music)
        }
        binding.viewRecent.setOnClickListener {
            navigateFragment(R.id.fragment_recently)
        }


    }


}