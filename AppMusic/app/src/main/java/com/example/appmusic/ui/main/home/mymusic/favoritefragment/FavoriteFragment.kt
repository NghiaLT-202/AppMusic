package com.example.appmusic.ui.main.home.mymusic.favoritefragment

import android.view.View
import com.example.tfmmusic.App

class FavoriteFragment : BaseBindingFragment<FragmentFavoriteBinding?, FavoriteViewModel?>() {
    private val listFavourite: MutableList<Music> = ArrayList<Music>()
    private var favoriteAdapter: FavoriteAdapter? = null
    val layoutId: Int
        get() = R.layout.fragment_favorite
    protected val viewModel: Class<FavoriteViewModel>
        protected get() = FavoriteViewModel::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        Timber.e("nghialt: onCreatedView Favourite")
        initAdapter()
        initListener()
        initData()
    }

    private fun initListener() {
        binding.imBack.setOnClickListener { v ->
            (requireActivity() as MainActivity).navController.navigate(
                R.id.fragment_home
            )
        }
    }

    private fun initAdapter() {
        favoriteAdapter = FavoriteAdapter()
        binding.rcFavorite.setAdapter(favoriteAdapter)
        favoriteAdapter.setIclickMusic(object : IclickMusic() {
            fun clickItem(position: Int) {
                App.getInstance().setMusicCurrent(listFavourite[position])
                val bundle = Bundle()
                bundle.putBoolean(Constant.RUN_NEW_MUSIC, true)
                (requireActivity() as MainActivity).navController.navigate(
                    R.id.fragment_detail_music,
                    bundle
                )
            }

            fun clickMenu(position: Int) {
                showBottomSheetDialog()
            }
        })
    }

    fun showBottomSheetDialog() {
        val bottomSheetFragment = BottomSheetListFuntionFrag()
        bottomSheetFragment.show(getChildFragmentManager(), null)
    }

    private fun initData() {
        viewModel.getAllMusicFavourite(true)
        viewModel.listFavourite.observe(getViewLifecycleOwner()) { songs ->
            listFavourite.clear()
            listFavourite.addAll(songs)
            if (listFavourite.size > 0) binding.tvNoDataFavorite.setVisibility(View.INVISIBLE) else binding.tvNoDataFavorite.setVisibility(
                View.VISIBLE
            )
            favoriteAdapter.setArrayList(songs)
        }
    }
}