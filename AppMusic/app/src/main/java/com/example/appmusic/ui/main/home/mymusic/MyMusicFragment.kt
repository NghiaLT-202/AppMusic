package com.example.appmusic.ui.main.home.mymusic

import android.graphics.Color
import android.view.View
import com.example.tfmmusic.R

class MyMusicFragment : BaseBindingFragment<FragmentMymusicBinding?, MyMusicViewmodel?>() {
    val layoutId: Int
        get() = R.layout.fragment_mymusic
    protected val viewModel: Class<MyMusicViewmodel>
        protected get() = MyMusicViewmodel::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
    }

    private fun initListener() {
        binding.tvSearch.setOnClickListener { v ->
            (requireActivity() as MainActivity).navController.navigate(
                R.id.fragment_research
            )
        }
        binding.imMenu.setOnClickListener { v -> binding.drawerLayout.openDrawer(GravityCompat.START) }
        binding.navView.setNavigationItemSelectedListener { item ->
            item.setChecked(true)
            binding.drawerLayout.closeDrawers()
            true
        }
        binding.viewFavourite.setOnClickListener { v ->
            (requireActivity() as MainActivity).navController.navigate(
                R.id.fragment_favorite
            )
        }
        binding.viewListPlay.setOnClickListener { v ->
            (requireActivity() as MainActivity).navController.navigate(
                R.id.fragment_list_music
            )
        }
        binding.viewRecent.setOnClickListener { v ->
            (requireActivity() as MainActivity).navController.navigate(
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
        val adapter = FragmentTabLayoutAdapter(getChildFragmentManager(), getLifecycle())
        binding.tabLayout.setTabTextColors(
            Color.parseColor("#80000000"),
            Color.parseColor("#000000")
        )
        binding.viewpager2.setAdapter(adapter)
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewpager2,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.setText(
                    list[position]
                )
            }).attach()
    }
}