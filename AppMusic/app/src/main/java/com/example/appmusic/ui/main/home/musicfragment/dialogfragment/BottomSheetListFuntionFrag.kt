package com.example.appmusic.ui.main.home.musicfragment.dialogfragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.BottomSheetListFuntionBinding
import com.example.appmusic.ui.base.BaseBottomSheetDialogFragment

class BottomSheetListFuntionFrag :
    BaseBottomSheetDialogFragment<BottomSheetListFuntionBinding, BottomSheetListFuntionVM>() {
    var music: Music? = null
    var namePlayList: String? = null
    override fun getViewModel(): Class<BottomSheetListFuntionVM> {
        return BottomSheetListFuntionVM::class.java
    }

    override val layoutId: Int
        get() = R.layout.bottom_sheet_list_funtion

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        initListener()
    }

    private fun initListener() {
//        binding.framelayout.setOnClickListener(v -> dismiss());
//        binding.addToPlayList.setOnClickListener(v -> {
//            BottomSheetAddPlayListFrag bottomSheetAddPlayListFrag = new BottomSheetAddPlayListFrag();
//            bottomSheetAddPlayListFrag.setMusicCurent(music);
//            bottomSheetAddPlayListFrag.show(getChildFragmentManager(), null);
//
//        });
    }

    private fun initData() {}
}