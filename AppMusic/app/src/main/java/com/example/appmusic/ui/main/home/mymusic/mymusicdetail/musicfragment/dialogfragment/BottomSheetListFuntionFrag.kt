package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.BottomSheetListFuntionBinding
import com.example.appmusic.ui.base.BaseBottomSheetDialogFragment
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.dialog.BottomSheetAddPlayListFrag

class BottomSheetListFuntionFrag :
    BaseBottomSheetDialogFragment<BottomSheetListFuntionBinding?, BottomSheetListFuntionVM>() {
    var music: Music? = null
    var namePlayList: String? = null
    override fun getViewModel(): Class<BottomSheetListFuntionVM>? {
        return BottomSheetListFuntionVM::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.bottom_sheet_list_funtion
    }


    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        initListener()
    }

    private fun initListener() {
        binding!!.framelayout.setOnClickListener { v: View? -> dismiss() }
        binding!!.addToPlayList.setOnClickListener { v: View? ->
            val bottomSheetAddPlayListFrag = BottomSheetAddPlayListFrag()
            bottomSheetAddPlayListFrag.setMusicCurent(music)
            bottomSheetAddPlayListFrag.show(childFragmentManager, null)
        }
    }

    private fun initData() {}
}