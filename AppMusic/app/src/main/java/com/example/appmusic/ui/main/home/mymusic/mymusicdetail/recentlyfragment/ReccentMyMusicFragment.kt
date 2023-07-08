package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.recentlyfragment

import android.R
import android.app.AlertDialog
import android.view.View
import com.example.tfmmusic.App

class ReccentMyMusicFragment : BaseBindingFragment<FragmentRecentlyBinding?, ReccentMyMusicVM?>() {
    var recentlyAdapter: RecentlyAdapter? = null
    private val recentList: MutableList<ItemRecent> = ArrayList<ItemRecent>()
    protected val viewModel: Class<ReccentMyMusicVM>
        protected get() = ReccentMyMusicVM::class.java
    protected val layoutId: Int
        protected get() = R.layout.fragment_recently

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()
    }

    private fun initListener() {
        binding.imBack.setOnClickListener { v -> (requireActivity() as MainActivity).navController.popBackStack() }
        binding.imDelete.setOnClickListener { v ->
            val alert = AlertDialog.Builder(getContext())
            alert.setTitle("Delete recent playlist")
            alert.setMessage("Do you want to delete recently played song")
            alert.setPositiveButton(
                R.string.yes,
                DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                    viewModel.deleteReccentMusic()
                    recentList.clear()
                    recentlyAdapter.setArrayList(recentList)
                    binding.tvNoDataReccent.setVisibility(View.VISIBLE)
                })
            alert.setNegativeButton(
                R.string.no,
                DialogInterface.OnClickListener { dialog: DialogInterface, which: Int -> dialog.cancel() })
            alert.show()
        }
    }

    private fun initAdapter() {
        recentlyAdapter = RecentlyAdapter()
        binding.rcPlayList.setAdapter(recentlyAdapter)
        recentlyAdapter.setIclickMusic(object : IclickMusic() {
            fun clickItem(position: Int) {
                val itemRecent: ItemRecent = recentList[position]
                val music = Music(
                    itemRecent.getMusicFile(),
                    itemRecent.getMusicName(),
                    itemRecent.getNameSinger(),
                    itemRecent.getNameAlbum(),
                    false,
                    itemRecent.getNamePlayList(),
                    null
                )
                music.setImageSong(itemRecent.getImageSong())
                App.getInstance().setMusicCurrent(music)
                (requireActivity() as MainActivity).navController.navigate(R.id.fragment_detail_music)
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
        mainViewModel.getAllReccentMusic()
        mainViewModel.listRecentLiveData.observe(getViewLifecycleOwner()) { listRecent ->
            if (listRecent != null) {
                recentList.clear()
                recentList.addAll(listRecent)
                if (recentList.size > 0) {
                    binding.tvNoDataReccent.setVisibility(View.INVISIBLE)
                }
                recentlyAdapter.setArrayList(recentList)
                mainViewModel.listRecentLiveData.postValue(null)
            }
        }
    }
}