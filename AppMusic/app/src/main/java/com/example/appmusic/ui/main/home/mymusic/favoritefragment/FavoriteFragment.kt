package com.example.appmusic.ui.main.home.mymusic.favoritefragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentFavoriteBinding
import com.example.appmusic.ui.adapter.FavoriteAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.BottomSheetListFuntionFrag
import timber.log.Timber

class FavoriteFragment : BaseBindingFragment<FragmentFavoriteBinding?, FavoriteViewModel>() {
    private val listFavourite: MutableList<Music?> = ArrayList()
    private var favoriteAdapter: FavoriteAdapter? = null


    override fun getViewModel(): Class<FavoriteViewModel>? {
        return FavoriteViewModel::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_favorite
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        Timber.e("nghialt: onCreatedView Favourite")
        initAdapter()
        initListener()
        initData()
    }

    private fun initListener() {
        binding!!.imBack.setOnClickListener { v: View? ->
            (requireActivity() as MainActivity).navController!!.navigate(
                R.id.fragment_home
            )
        }
    }

    private fun initAdapter() {
        favoriteAdapter = FavoriteAdapter()
        binding!!.rcFavorite.adapter = favoriteAdapter
        favoriteAdapter!!.setIclickMusic(object : FavoriteAdapter.IclickMusic {
            override fun clickItem(position: Int) {
                App.Companion.instance.musicCurrent=(listFavourite[position])
                (requireActivity() as MainActivity).navController!!.navigate(R.id.fragment_detail_music)
            }

            override fun clickMenu(position: Int) {
                showBottomSheetDialog()
            }
        })
    }

    fun showBottomSheetDialog() {
        val bottomSheetFragment = BottomSheetListFuntionFrag()
        bottomSheetFragment.show(childFragmentManager, null)
    }

    private fun initData() {
        viewModel!!.getAllMusicFavourite(true)
        viewModel!!.listFavourite.observe(viewLifecycleOwner) { songs ->
            listFavourite.clear()
            listFavourite.addAll(songs!!)
            if (listFavourite.size > 0) binding!!.tvNoDataFavorite.visibility =
                View.INVISIBLE else binding!!.tvNoDataFavorite.visibility = View.VISIBLE
            favoriteAdapter?.list=(songs.toMutableList())
        }
    }
}