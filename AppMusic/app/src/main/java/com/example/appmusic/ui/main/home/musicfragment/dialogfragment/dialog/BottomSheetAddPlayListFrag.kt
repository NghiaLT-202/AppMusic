package com.example.appmusic.ui.main.home.musicfragment.dialogfragment.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appmusic.R
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.data.model.DataPlayList
import com.example.appmusic.databinding.BottomSheetAddPlaylistBinding
import com.example.appmusic.ui.adapter.ListPlayListAdapter
import com.example.appmusic.ui.base.BaseBottomSheetDialogFragment
import com.example.appmusic.ui.main.MainActivity
import timber.log.Timber

class BottomSheetAddPlayListFrag :
    BaseBottomSheetDialogFragment<BottomSheetAddPlaylistBinding, BottomSheetAddPlayListVM>() {
    private var listPlayListAdapter: ListPlayListAdapter? = null
    private val listDataPlayList: MutableList<DataPlayList> = mutableListOf()
    private val listDataMusicPlayList: MutableList<DataMusic> = mutableListOf()
    private var positionPlayList = 0
    var dataMusicCurent: DataMusic? = null
    override fun getViewModel(): Class<BottomSheetAddPlayListVM> {
        return BottomSheetAddPlayListVM::class.java
    }

    override val layoutId: Int
        get() = R.layout.bottom_sheet_add_playlist

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()
    }

    private fun initAdapter() {
        listPlayListAdapter = ListPlayListAdapter()
        binding.rcListCollection.adapter = listPlayListAdapter
        listPlayListAdapter?.clickItemView = {position->
            positionPlayList = position
            viewModel.getAllMusicPlayList(listDataPlayList[position].namePlayList)
            Timber.e("ltnghia")
        }


    }

    private fun initListener() {

        binding.framelayout.setOnClickListener { dismiss() }
        binding.tvConllectionNew.setOnClickListener {
            (requireActivity() as MainActivity).navController?.navigate(
                R.id.fragment_list_music
            )
        }
    }

    private fun initData() {
        mainViewModel.getAllPlayList()
        mainViewModel.listPlaylistData.observe(viewLifecycleOwner) { playLists ->
            listDataPlayList.clear()
            listDataPlayList.addAll(playLists)
            listPlayListAdapter!!.listPlay = (listDataPlayList)
        }
        viewModel.listDataMusicPlaylist.observe(viewLifecycleOwner) { dataMusics: MutableList<DataMusic> ->
            val dataMusicCurrentName = dataMusicCurent?.musicName
            val isMusicInPlaylist = dataMusics.any { it.musicName == dataMusicCurrentName }

            if (!isMusicInPlaylist) {
                Timber.e("ltnghia"+isMusicInPlaylist)

                dataMusicCurent?.namePlayList = listDataPlayList[positionPlayList].namePlayList
                viewModel.inSertMusicofPlayList(dataMusicCurent!!)
                Toast.makeText(context, getString(R.string.success), Toast.LENGTH_SHORT).show()
            } else {
                Timber.e("ltnghia")

                Toast.makeText(context, getString(R.string.no_add), Toast.LENGTH_SHORT).show()
            }

            dismiss()
        }
    }
}