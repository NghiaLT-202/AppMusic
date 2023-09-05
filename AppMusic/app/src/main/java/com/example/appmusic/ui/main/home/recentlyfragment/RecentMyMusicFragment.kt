package com.example.appmusic.ui.main.home.recentlyfragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.data.model.DataItemRecent
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.FragmentRecentlyBinding
import com.example.appmusic.ui.adapter.RecentlyAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.musicfragment.dialogfragment.BottomSheetOptionsFragment

@Suppress("DEPRECATION")
class RecentMyMusicFragment : BaseBindingFragment<FragmentRecentlyBinding, RecentMyMusicVM>() {
    private val recentlyAdapter: RecentlyAdapter by lazy { RecentlyAdapter() }
    private val recentList: MutableList<DataItemRecent> = mutableListOf()
    private val bottomSheetFragment :  BottomSheetOptionsFragment by lazy {  BottomSheetOptionsFragment() }

    override fun getViewModel(): Class<RecentMyMusicVM> {
        return RecentMyMusicVM::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_recently

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()
    }

    private fun initListener() {
        binding.imBack.setOnClickListener { (requireActivity() as MainActivity).navController.popBackStack() }
        binding.imDelete.setOnClickListener {
            val alert = AlertDialog.Builder(context)
            alert.setTitle(getString(R.string.delete_recent_playlist))
            alert.setMessage(getString(R.string.do_you_want_to_delete_recently_played_song))
            alert.setPositiveButton(android.R.string.yes) { _: DialogInterface?, _: Int ->
                viewModel.deleteRecentMusic()
                recentList.clear()
                recentlyAdapter.list = (recentList)
                binding.tvNoDataReccent.visibility = View.VISIBLE
            }
            alert.setNegativeButton(android.R.string.no) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            alert.show()
        }
    }

    private fun initAdapter() {
        binding.rcPlayList.adapter = recentlyAdapter
        recentlyAdapter.setIclickMusic(object : RecentlyAdapter.IclickMusic {
            override fun clickItem(position: Int) {
                val itemRecent = recentList[position]
                DataMusic().apply {
                    musicFile = itemRecent.musicFile
                    musicName = itemRecent.musicName
                    nameSinger = itemRecent.nameSinger
                    nameAlbum = itemRecent.nameAlbum
                    imageSong = itemRecent.imageSong
                    checkFavorite = false
                    namePlayList = itemRecent.namePlayList
                    date = null.toString()
                    App.instance.musicCurrent = (this)
                }
                Bundle().apply {
                    putBoolean(Constant.RUN_NEW_MUSIC, true)
                    navigateFragmentAndBundle( R.id.fragment_detail_music,
                            this)

                }
            }

            override fun clickMenu(position: Int) {
                showBottomSheetDialog()
            }
        })
    }

    fun showBottomSheetDialog() {
        bottomSheetFragment.show(childFragmentManager, null)
    }

    private fun initData() {
        mainViewModel.getAllRecentMusic()
        mainViewModel.listRecentLiveData.observe(viewLifecycleOwner) { listRecent ->
            if (listRecent != null) {
                recentList.clear()
                recentList.addAll(listRecent)
                if (recentList.size > 0) {
                    binding.tvNoDataReccent.visibility = View.INVISIBLE
                }
                recentlyAdapter.list = (recentList)
                mainViewModel.listRecentLiveData.postValue(null)
            }
        }
    }
}