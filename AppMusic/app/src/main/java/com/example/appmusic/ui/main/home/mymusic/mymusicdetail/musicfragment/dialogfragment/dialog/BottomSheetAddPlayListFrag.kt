package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.dialog

import android.view.View
import com.example.tfmmusic.R

@AndroidEntryPoint
class BottomSheetAddPlayListFrag :
    BaseBottomSheetDialogFragment<BottomSheetAddPlaylistBinding?, BottomSheetAddPlayListVM?>() {
    private var listPlayListAdapter: ListPlayListAdapter? = null
    private val listPlayList: MutableList<PlayList> = ArrayList<PlayList>()
    private val listMusicPlayList: MutableList<Music> = ArrayList<Music>()
    private var positionPlayList = 0
    private var musicCurent: Music? = null
    fun setMusicCurent(musicCurent: Music?) {
        this.musicCurent = musicCurent
    }

    protected val viewModel: Class<BottomSheetAddPlayListVM>
        protected get() = BottomSheetAddPlayListVM::class.java
    val layoutId: Int
        get() = R.layout.bottom_sheet_add_playlist

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()
    }

    private fun initAdapter() {
        listPlayListAdapter = ListPlayListAdapter()
        binding.rcListCollection.setAdapter(listPlayListAdapter)
        listPlayListAdapter.setiBaseClickAdapter { position ->
            positionPlayList = position
            viewModel.getAllMusicPlayList(listPlayList[position].getNamePlayList())
        }
    }

    private fun initListener() {
        binding.framelayout.setOnClickListener { v -> dismiss() }
        binding.tvConllectionNew.setOnClickListener { v ->
            (requireActivity() as MainActivity).navController.navigate(
                R.id.fragment_list_music
            )
        }
    }

    private fun initData() {
        viewModel.getAllPlayList()
        viewModel.listPlaylist.observe(getViewLifecycleOwner()) { playLists ->
            listPlayList.clear()
            listPlayList.addAll(playLists)
            listPlayListAdapter.setListPlay(listPlayList)
        }
        viewModel.listMusicPlaylist.observe(getViewLifecycleOwner()) { music ->
            listMusicPlayList.clear()
            listMusicPlayList.addAll(music)
            var check = false
            for (i in listMusicPlayList.indices) {
                if (musicCurent.getMusicName().equals(listMusicPlayList[i].getMusicName())) {
                    check = true
                    break
                }
            }
            if (!check) {
                musicCurent.setNamePlayList(listPlayList[positionPlayList].getNamePlayList())
                viewModel.inSertMusicofPlayList(musicCurent)
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getContext(), "No add", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }
    }
}