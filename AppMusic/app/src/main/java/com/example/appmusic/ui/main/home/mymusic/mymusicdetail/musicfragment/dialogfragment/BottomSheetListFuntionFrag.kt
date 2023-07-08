package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment

import android.view.View
import com.example.tfmmusic.R

class BottomSheetListFuntionFrag :
    BaseBottomSheetDialogFragment<BottomSheetListFuntionBinding?, BottomSheetListFuntionVM?>() {
    private var music: Music? = null
    var namePlayList: String? = null
    fun getMusic(): Music? {
        return music
    }

    fun setMusic(music: Music?) {
        this.music = music
    }

    protected val viewModel: Class<BottomSheetListFuntionVM>
        protected get() = BottomSheetListFuntionVM::class.java
    val layoutId: Int
        get() = R.layout.bottom_sheet_list_funtion

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        initListener()
    }

    private fun initListener() {
        binding.framelayout.setOnClickListener { v -> dismiss() }
        binding.addToPlayList.setOnClickListener { v ->
            val bottomSheetAddPlayListFrag = BottomSheetAddPlayListFrag()
            bottomSheetAddPlayListFrag.setMusicCurent(music)
            bottomSheetAddPlayListFrag.show(getChildFragmentManager(), null)
        }
        binding.delete.setOnClickListener(View.OnClickListener { Timber.e("nghialt: delete") })
        binding.share.setOnClickListener(View.OnClickListener { Timber.e("nghialt: share") })
    }

    private fun initData() {}
}