package com.example.appmusic.ui.main.home.mymusic.playlistfragment

import android.view.View
import com.example.tfmmusic.R

class PlaylistFragment : BaseBindingFragment<FragmentPlayListMusicBinding?, PlaylistViewModel?>() {
    private val listPlayList: MutableList<PlayList> = ArrayList<PlayList>()
    private val listMusicPlaylist: MutableList<Music> = ArrayList<Music>()
    private var playlistAdapter: PlaylistAdapter? = null
    private var dialogAddPlayListFragment: DialogAddPlayListFragment? = null
    val layoutId: Int
        get() = R.layout.fragment_play_list_music
    protected val viewModel: Class<PlaylistViewModel>
        protected get() = PlaylistViewModel::class.java

    fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initlistener()
        initAdapter()
        intitData()
    }

    private fun initAdapter() {
        playlistAdapter = PlaylistAdapter()
        binding.rcPlayList.setAdapter(playlistAdapter)
        playlistAdapter.setIclickMenu(object : IclickMenu() {
            fun clickMenu(location: IntArray, view: View?, playList: PlayList, position: Int) {
                val optionPlayListDialog = OptionPlayListDialog()
                optionPlayListDialog.setiOptionCollectionDialog(object : IOptionCollectionDialog() {
                    fun delete() {
                        viewModel.deletePlayList(playList.getNamePlayList())
                        listPlayList.remove(playList)
                        if (listPlayList.size < 1) {
                            binding.tvAddPlayList.setVisibility(View.VISIBLE)
                            binding.tvPlayRandom.setVisibility(View.INVISIBLE)
                            binding.imAddPlayList.setVisibility(View.INVISIBLE)
                        }
                        playlistAdapter.setListPlayList(listPlayList)
                    }

                    fun edit() {
                        showDialogEditPlayList(position)
                    }
                })
                optionPlayListDialog.showDialog(location[0], location[1], getChildFragmentManager())
            }

            fun clickItem(position: Int) {
                val bundle = Bundle()
                bundle.putString(Constant.NAME_PLAYLIST, listPlayList[position].getNamePlayList())
                (requireActivity() as MainActivity).navController.navigate(
                    R.id.fragment_detail_playlist,
                    bundle
                )
            }
        })
    }

    private fun intitData() {
        viewModel.getAllPlayList()
        viewModel.listPlayList.observe(getViewLifecycleOwner()) { playLists ->
            if (playLists != null) {
                listPlayList.clear()
                listPlayList.addAll(playLists)
                for (i in listPlayList.indices) {
                    viewModel.getAllMusicPlayList(listPlayList[i].getNamePlayList())
                }
                playlistAdapter.setListPlayList(listPlayList)
                if (listPlayList.size > 0) {
                    binding.tvAddPlayList.setVisibility(View.INVISIBLE)
                    binding.tvPlayRandom.setVisibility(View.VISIBLE)
                    binding.imAddPlayList.setVisibility(View.VISIBLE)
                }
            }
        }
        viewModel.listMusicPlaylist.observe(getViewLifecycleOwner()) { music ->
            listMusicPlaylist.clear()
            listMusicPlaylist.addAll(music)
            playlistAdapter.setTotalSong(listMusicPlaylist.size)
        }
    }

    private fun initlistener() {
        binding.imBack.setOnClickListener { v -> (requireActivity() as MainActivity).navController.popBackStack() }
        binding.tvAddPlayList.setOnClickListener { v -> showDialogAddPlayList() }
        binding.imAddPlayList.setOnClickListener { v -> showDialogAddPlayList() }
    }

    fun showDialogEditPlayList(position: Int) {
        dialogAddPlayListFragment = DialogAddPlayListFragment()
        dialogAddPlayListFragment.setText(getString(R.string.edit))
        dialogAddPlayListFragment.setTextEdit(listPlayList[position].getNamePlayList())
        dialogAddPlayListFragment.setCancelable(true)
        dialogAddPlayListFragment.setiDialogAdd(object : IDialogAdd() {
            fun cancle(position: Int) {
                dialogAddPlayListFragment.dismiss()
            }

            fun ok(inputName: String) {
                if (!TextUtils.isEmpty(inputName)) {
                    if (!checkNamePlayList(inputName)) {
                        viewModel.updateNamePlayList(
                            inputName,
                            listPlayList[position].getIdPlayList()
                        )
                        listPlayList[position].setNamePlayList(inputName)
                        if (listPlayList.size > 0) {
                            binding.tvAddPlayList.setVisibility(View.INVISIBLE)
                        }
                        playlistAdapter.setListPlayList(listPlayList)
                        dialogAddPlayListFragment.dismiss()
                    } else {
                        toast(getString(R.string.listNameAlreadyExists))
                    }
                } else {
                    toast(getString(R.string.pleaseEnterListName))
                }
            }
        })
        dialogAddPlayListFragment.show(getParentFragmentManager(), null)
    }

    fun showDialogAddPlayList() {
        dialogAddPlayListFragment = DialogAddPlayListFragment()
        dialogAddPlayListFragment.setCancelable(true)
        dialogAddPlayListFragment.setiDialogAdd(object : IDialogAdd() {
            fun cancle(position: Int) {
                dialogAddPlayListFragment.dismiss()
            }

            fun ok(inputName: String) {
                if (!TextUtils.isEmpty(inputName)) {
                    val playList: PlayList
                    playList = PlayList(inputName, null, 0)
                    if (!checkNamePlayList(inputName)) {
                        viewModel.insertPlayList(playList)
                        dialogAddPlayListFragment.dismiss()
                        listPlayList.add(playList)
                        listPlayList.size
                        binding.tvAddPlayList.setVisibility(View.INVISIBLE)
                        binding.tvPlayRandom.setVisibility(View.VISIBLE)
                        binding.imAddPlayList.setVisibility(View.VISIBLE)
                        playlistAdapter.setListPlayList(listPlayList)
                    } else {
                        toast(getString(R.string.listNameAlreadyExists))
                    }
                } else {
                    toast(getString(R.string.pleaseEnterListName))
                }
            }
        })
        dialogAddPlayListFragment.show(getParentFragmentManager(), null)
    }

    fun checkNamePlayList(inputName: String): Boolean {
        for (i in listPlayList.indices) {
            if (inputName == listPlayList[i].getNamePlayList()) {
                return true
            }
        }
        return false
    }
}