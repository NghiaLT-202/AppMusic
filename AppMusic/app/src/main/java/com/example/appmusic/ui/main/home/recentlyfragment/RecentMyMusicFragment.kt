package com.example.appmusic.ui.main.home.recentlyfragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentRecentlyBinding
import com.example.appmusic.ui.adapter.RecentlyAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.musicfragment.dialogfragment.BottomSheetOptionsFragment

class RecentMyMusicFragment : BaseBindingFragment<FragmentRecentlyBinding, RecentMyMusicVM>() {
    var recentlyAdapter: RecentlyAdapter? = null
    private val recentList: MutableList<ItemRecent> = mutableListOf()
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
        binding.imBack.setOnClickListener { v: View? -> (requireActivity() as MainActivity).navController!!.popBackStack() }
        binding.imDelete.setOnClickListener { v: View? ->
            val alert = AlertDialog.Builder(context)
            alert.setTitle("Delete recent playlist")
            alert.setMessage("Do you want to delete recently played song")
            alert.setPositiveButton(android.R.string.yes) { dialog: DialogInterface?, which: Int ->
                viewModel.deleteRecentMusic()
                recentList.clear()
                recentlyAdapter?.list = (recentList)
                binding.tvNoDataReccent.visibility = View.VISIBLE
            }
            alert.setNegativeButton(android.R.string.no) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            alert.show()
        }
    }

    private fun initAdapter() {
        recentlyAdapter = RecentlyAdapter()
        binding.rcPlayList.adapter = recentlyAdapter
        recentlyAdapter!!.setIclickMusic(object : RecentlyAdapter.IclickMusic {
            override fun clickItem(position: Int) {
                val itemRecent = recentList[position]
                Music().apply {
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
                (requireActivity() as MainActivity).navController!!.navigate(R.id.fragment_detail_music)
            }

            override fun clickMenu(position: Int) {
                showBottomSheetDialog()
            }
        })
    }

    fun showBottomSheetDialog() {
        val bottomSheetFragment = BottomSheetOptionsFragment()
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
                recentlyAdapter?.list = (recentList)
                mainViewModel.listRecentLiveData.postValue(null)
            }
        }
    }
}