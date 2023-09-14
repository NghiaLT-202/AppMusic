package com.example.appmusic.ui.main.home.favoritefragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.FragmentFavoriteBinding
import com.example.appmusic.ui.adapter.FavoriteAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.musicfragment.dialogfragment.BottomSheetOptionsFragment

class FavoriteFragment : BaseBindingFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    private val listFavourite: MutableList<DataMusic> = mutableListOf()
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter().apply {
            binding.rcFavorite.adapter = this
        }
    }
    override val layoutId: Int
        get() = R.layout.fragment_favorite

    override fun getViewModel(): Class<FavoriteViewModel> {
        return FavoriteViewModel::class.java
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()
    }

    private fun initListener() {
        binding.imBack.setOnClickListener {
            navigateFragment(R.id.fragment_home
            )

        }
    }

    private fun initAdapter() {
        favoriteAdapter.clickItem = { position, _ ->
            App.instance.musicCurrent = (listFavourite[position])
            Bundle().apply {
                putBoolean(Constant.RUN_NEW_MUSIC, true)
                navigateFragmentAndBundle(R.id.fragment_detail_music, this)
            }
        }
        favoriteAdapter.clickMenu = { _, _ ->
            showBottomSheetDialog()

        }

    }

    private fun showBottomSheetDialog() {
        val bottomSheetFragment = BottomSheetOptionsFragment()
        bottomSheetFragment.show(childFragmentManager, null)
    }

    private fun initData() {
        viewModel.getAllMusicFavourite(true)
        viewModel.listFavourite.observe(viewLifecycleOwner) { songs ->
            listFavourite.clear()
            listFavourite.addAll(songs)
            if (listFavourite.size > 0) binding.tvNoDataFavorite.visibility =
                    View.INVISIBLE else binding.tvNoDataFavorite.visibility = View.VISIBLE
            favoriteAdapter.list = (songs)
        }
    }
}