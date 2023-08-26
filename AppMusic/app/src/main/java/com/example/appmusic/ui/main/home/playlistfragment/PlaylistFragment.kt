package com.example.appmusic.ui.main.home.playlistfragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.data.model.DataPlayList
import com.example.appmusic.databinding.FragmentPlayListMusicBinding
import com.example.appmusic.ui.adapter.PlaylistAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.dialog.OptionPlayListDialog
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.playlistfragment.dialogFragmentAddPlayList.DialogAddPlayListFragment

class PlaylistFragment : BaseBindingFragment<FragmentPlayListMusicBinding, PlaylistViewModel>() {
    private val listDataPlayList: MutableList<DataPlayList> = mutableListOf()
    private val listDataMusicPlaylist: MutableList<DataMusic> = mutableListOf()
    private var playlistAdapter: PlaylistAdapter? = null
    private var dialogAddPlayListFragment: DialogAddPlayListFragment? = null
    override val layoutId: Int
        get() = R.layout.fragment_play_list_music

    override fun getViewModel(): Class<PlaylistViewModel> {
        return PlaylistViewModel::class.java
    }


    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initListener()
        initAdapter()
        initData()

    }

    private fun initAdapter() {
        playlistAdapter = PlaylistAdapter()
        with(binding) {
            rcPlayList.adapter = playlistAdapter
            playlistAdapter?.setIclickMenu(object : PlaylistAdapter.IclickMenu {
                override fun clickMenu(
                    location: IntArray,
                    view: View,
                    dataPlayList: DataPlayList,
                    position: Int
                ) {
                    val optionPlayListDialog = OptionPlayListDialog()

                    optionPlayListDialog.delete = {
                        dataPlayList.namePlayList.let { viewModel.deletePlayList(it) }
                        listDataPlayList.remove(dataPlayList)
                        if (listDataPlayList.size < 1) {
                            tvAddPlayList.visibility = View.VISIBLE
                            tvPlayRandom.visibility = View.INVISIBLE
                            imAddPlayList.visibility = View.INVISIBLE
                        }
                        playlistAdapter?.listDataPlayList = (listDataPlayList)
                    }
                    optionPlayListDialog.edit = {
                        showDialogEditPlayList(position)

                    }

                    optionPlayListDialog.showDialog(
                        location[0].toFloat(),
                        location[1].toFloat(), childFragmentManager
                    )
                }

                override fun clickItem(position: Int) {
                    val bundle = Bundle()
                    bundle.putString(
                        Constant.NAME_PLAYLIST,
                        listDataPlayList[position].namePlayList
                    )
                    (requireActivity() as MainActivity).navController?.navigate(
                        R.id.fragment_detail_playlist,
                        bundle
                    )
                }
            })
        }

    }

    private fun initData() {
        mainViewModel.getAllPlayList()
        mainViewModel.listPlaylistData.observe(viewLifecycleOwner) { playLists ->
            if (playLists != null) {
                listDataPlayList.clear()
                listDataPlayList.addAll(playLists)
                for (i in listDataPlayList.indices) {
                    viewModel.getAllMusicPlayList(listDataPlayList[i].namePlayList)
                }
                playlistAdapter?.listDataPlayList = (listDataPlayList)
                if (listDataPlayList.size > 0) {
                    with(binding) {
                        tvAddPlayList.visibility = View.INVISIBLE
                        tvPlayRandom.visibility = View.VISIBLE
                        imAddPlayList.visibility = View.VISIBLE
                    }

                }
            }
        }
        viewModel.listDataMusicPlaylist.observe(viewLifecycleOwner) { music ->
            listDataMusicPlaylist.clear()
            listDataMusicPlaylist.addAll(music)
            playlistAdapter?.totalSong = listDataMusicPlaylist.size
        }
    }

    private fun initListener() {
        with(binding) {
            imBack.setOnClickListener {
                (requireActivity() as MainActivity).navController?.popBackStack()
            }
            tvAddPlayList.setOnClickListener { showDialogAddPlayList() }
            imAddPlayList.setOnClickListener { showDialogAddPlayList() }
        }

    }

    fun showDialogEditPlayList(position: Int) {
        dialogAddPlayListFragment = DialogAddPlayListFragment()
        dialogAddPlayListFragment?.text = (getString(R.string.edit))
        dialogAddPlayListFragment?.textEdit = (listDataPlayList[position].namePlayList)
        dialogAddPlayListFragment?.isCancelable = true
        dialogAddPlayListFragment?.setDialogAdd(object : DialogAddPlayListFragment.IDialogAdd {
            override fun candle(position: Int) {
                dialogAddPlayListFragment?.dismiss()
            }


            override fun ok(inputName: String) {
                if (!TextUtils.isEmpty(inputName)) {
                    if (!checkNamePlayList(inputName)) {
                        viewModel.updateNamePlayList(
                            inputName,
                            listDataPlayList[position].idPlayList
                        )
                        listDataPlayList[position].namePlayList = (inputName)
                        if (listDataPlayList.size > 0) {
                            binding.tvAddPlayList.visibility = View.INVISIBLE
                        }
                        playlistAdapter?.listDataPlayList = (listDataPlayList)
                        dialogAddPlayListFragment?.dismiss()
                    } else {
                        toast(getString(R.string.listNameAlreadyExists))
                    }
                } else {
                    toast(getString(R.string.pleaseEnterListName))
                }
            }

        })
        dialogAddPlayListFragment?.show(parentFragmentManager, null)
    }

    private fun showDialogAddPlayList() {
        dialogAddPlayListFragment = DialogAddPlayListFragment()
        dialogAddPlayListFragment?.isCancelable = true
        dialogAddPlayListFragment?.setDialogAdd(object : DialogAddPlayListFragment.IDialogAdd {
            override fun candle(position: Int) {
                dialogAddPlayListFragment?.dismiss()
            }

            override fun ok(inputName: String) {
                if (!TextUtils.isEmpty(inputName)) {
                    DataPlayList().apply {
                        namePlayList = inputName
                        pathPlayList = ""
                        totalSong = 0
                        if (!checkNamePlayList(inputName)) {
                            viewModel.insertPlayList(this)
                            mainViewModel.listPlaylistData.value?.add(this)
                            mainViewModel.listPlaylistData.postValue(mainViewModel.listPlaylistData.value)
                            dialogAddPlayListFragment?.dismiss()
                            listDataPlayList.add(this)
                            listDataPlayList.size
                            binding.tvAddPlayList.visibility = View.INVISIBLE
                            binding.tvPlayRandom.visibility = View.VISIBLE
                            binding.imAddPlayList.visibility = View.VISIBLE
                            playlistAdapter?.listDataPlayList = (listDataPlayList)
                        } else {
                            toast(getString(R.string.listNameAlreadyExists))
                        }
                    }

                } else {
                    toast(getString(R.string.pleaseEnterListName))
                }
            }
        })
        dialogAddPlayListFragment?.show(parentFragmentManager, null)
    }


    fun checkNamePlayList(inputName: String): Boolean {
        for (i in listDataPlayList.indices) {
            if (inputName == listDataPlayList[i].namePlayList) {
                return true
            }
        }
        return false
    }
}