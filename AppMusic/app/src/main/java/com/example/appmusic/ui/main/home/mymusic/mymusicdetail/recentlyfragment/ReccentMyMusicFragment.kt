package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.recentlyfragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentRecentlyBinding
import com.example.appmusic.ui.adapter.FavoriteAdapter
import com.example.appmusic.ui.adapter.RecentlyAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.BottomSheetListFuntionFrag

class ReccentMyMusicFragment : BaseBindingFragment<FragmentRecentlyBinding?, ReccentMyMusicVM>() {
    var recentlyAdapter: RecentlyAdapter? = null
    private val recentList: MutableList<ItemRecent?> = ArrayList()
    override fun getViewModel(): Class<ReccentMyMusicVM>? {
        return ReccentMyMusicVM::class.java
    }

    protected override val layoutId: Int
        protected get() = R.layout.fragment_recently

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()
    }

    private fun initListener() {
        binding!!.imBack.setOnClickListener { v: View? -> (requireActivity() as MainActivity).navController!!.popBackStack() }
        binding!!.imDelete.setOnClickListener { v: View? ->
            val alert = AlertDialog.Builder(context)
            alert.setTitle("Delete recent playlist")
            alert.setMessage("Do you want to delete recently played song")
            alert.setPositiveButton(android.R.string.yes) { dialog: DialogInterface?, which: Int ->
                viewModel!!.deleteReccentMusic()
                recentList.clear()
                recentlyAdapter!!.setArrayList(recentList)
                binding!!.tvNoDataReccent.visibility = View.VISIBLE
            }
            alert.setNegativeButton(android.R.string.no) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            alert.show()
        }
    }

    private fun initAdapter() {
        recentlyAdapter = RecentlyAdapter()
        binding!!.rcPlayList.adapter = recentlyAdapter
        recentlyAdapter!!.setIclickMusic(object : FavoriteAdapter.IclickMusic {
            override fun clickItem(position: Int) {
                val itemRecent = recentList[position]
                val music = Music(
                    itemRecent.getMusicFile(),
                    itemRecent.getMusicName(),
                    itemRecent.getNameSinger(),
                    itemRecent.getNameAlbum(),
                    false,
                    itemRecent.getNamePlayList(),
                    null
                )
                music.imageSong = itemRecent.getImageSong()
                App.Companion.getInstance().setMusicCurrent(music)
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
        mainViewModel.getAllReccentMusic()
        mainViewModel!!.listRecentLiveData.observe(viewLifecycleOwner) { listRecent: List<ItemRecent?>? ->
            if (listRecent != null) {
                recentList.clear()
                recentList.addAll(listRecent)
                if (recentList.size > 0) {
                    binding!!.tvNoDataReccent.visibility = View.INVISIBLE
                }
                recentlyAdapter!!.setArrayList(recentList)
                mainViewModel!!.listRecentLiveData.postValue(null)
            }
        }
    }
}