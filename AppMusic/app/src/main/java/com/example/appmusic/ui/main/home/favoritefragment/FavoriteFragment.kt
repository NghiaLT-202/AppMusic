package com.example.appmusic.ui.main.home.favoritefragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentFavoriteBinding
import com.example.appmusic.ui.adapter.FavoriteAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.musicfragment.dialogfragment.BottomSheetListFuntionFrag

class FavoriteFragment : BaseBindingFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    private val listFavourite: MutableList<Music> = mutableListOf()
    private var favoriteAdapter: FavoriteAdapter? = null
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
            (requireActivity() as MainActivity).navController!!.navigate(
                R.id.fragment_home
            )
        }
    }

    private fun initAdapter() {
        FavoriteAdapter().apply {
            binding.rcFavorite.adapter = this
            this.setIClickMusic(object : FavoriteAdapter.IclickMusic {
                override fun clickItem(position: Int) {
                    App.instance.musicCurrent = (listFavourite[position])
                    Bundle().apply {
                        putBoolean(Constant.RUN_NEW_MUSIC, true)
                        (requireActivity() as MainActivity).navController?.navigate(
                            R.id.fragment_detail_music,
                            this
                        )
                    }
                }

                override fun clickMenu(position: Int) {
                    showBottomSheetDialog()
                }
            })
        }

    }

    fun showBottomSheetDialog() {
        val bottomSheetFragment = BottomSheetListFuntionFrag()
        bottomSheetFragment.show(childFragmentManager, null)
    }

    private fun initData() {
        viewModel.getAllMusicFavourite(true)
        viewModel.listFavourite.observe(viewLifecycleOwner) { songs ->
            listFavourite.clear()
            listFavourite.addAll(songs)
            if (listFavourite.size > 0) binding.tvNoDataFavorite.visibility =
                View.INVISIBLE else binding.tvNoDataFavorite.visibility = View.VISIBLE
            favoriteAdapter?.list = (songs)
        }
    }
}