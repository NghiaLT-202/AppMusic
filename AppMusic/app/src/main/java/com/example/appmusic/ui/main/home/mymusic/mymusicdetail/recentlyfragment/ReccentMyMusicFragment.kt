package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.recentlyfragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.databinding.FragmentRecentlyBinding
import com.example.appmusic.ui.adapter.FavoriteAdapter
import com.example.appmusic.ui.adapter.RecentlyAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.BottomSheetListFuntionFrag

class ReccentMyMusicFragment : BaseBindingFragment<FragmentRecentlyBinding, ReccentMyMusicVM>() {
    private lateinit var recentlyAdapter: RecentlyAdapter

    private val recentList: MutableList<ItemRecent> = mutableListOf()

    override fun getViewModel(): Class<ReccentMyMusicVM> {
        return ReccentMyMusicVM::class.java
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_recently
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()    }


    private fun initListener() {
        binding.imBack.setOnClickListener {
            (requireActivity() as MainActivity).navController?.popBackStack()
        }

        binding.imDelete.setOnClickListener {
            val alert = AlertDialog.Builder(context)
            alert.setTitle("Delete recent playlist")
            alert.setMessage("Do you want to delete recently played song")
            alert.setPositiveButton(android.R.string.yes) { dialog, which ->
                viewModel.deleteReccentMusic()
                recentList.clear()
                recentlyAdapter.list=(recentList.toMutableList())
                binding.tvNoDataReccent.visibility = View.VISIBLE
            }
            alert.setNegativeButton(android.R.string.no) { dialog, which ->
                dialog.cancel()
            }
            alert.show()
        }
    }

    private fun initAdapter() {
        recentlyAdapter = RecentlyAdapter()
        binding.rcPlayList.adapter = recentlyAdapter
        recentlyAdapter.setIclickMusic(object : FavoriteAdapter.IclickMusic {
            override fun clickItem(position: Int) {
                val itemRecent = recentList[position]
                val music = Music(
                    itemRecent.musicFile,
                    itemRecent.musicName,
                    itemRecent.nameSinger,
                    itemRecent.nameAlbum,
                    false,
                    itemRecent.namePlayList,
                    null
                )
                music.imageSong = itemRecent.imageSong
                App.instance.musicCurrent=music
                (requireActivity() as MainActivity).navController?.navigate(R.id.fragment_detail_music)
            }

            override fun clickMenu(position: Int) {
                showBottomSheetDialog()
            }
        })
    }

    private fun showBottomSheetDialog() {
        val bottomSheetFragment = BottomSheetListFuntionFrag()
        bottomSheetFragment.show(childFragmentManager, null)
    }

    private fun initData() {
        mainViewModel.allReccentMusic()
        mainViewModel.listRecentLiveData.observe(viewLifecycleOwner, Observer { listRecent ->
            if (listRecent != null) {
                recentList.clear()
                recentList.addAll(listRecent)
                if (recentList.size > 0) {
                    binding.tvNoDataReccent.visibility = View.INVISIBLE
                }
                recentlyAdapter.list=(recentList.toMutableList())
                mainViewModel.listRecentLiveData.postValue(null)
            }
        })
    }
}