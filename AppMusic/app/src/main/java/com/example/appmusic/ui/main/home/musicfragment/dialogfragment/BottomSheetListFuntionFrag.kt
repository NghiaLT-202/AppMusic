package com.example.appmusic.ui.main.home.musicfragment.dialogfragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.BottomSheetListFuntionBinding
import com.example.appmusic.ui.base.BaseBottomSheetDialogFragment
import com.example.appmusic.ui.main.home.musicfragment.dialogfragment.dialog.BottomSheetAddPlayListFrag
import timber.log.Timber

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
        binding.framelayout.setOnClickListener {  dismiss() }
        binding.addToPlayList.setOnClickListener {
            val bottomSheetAddPlayListFrag = BottomSheetAddPlayListFrag()
            Timber.e("ltnghia"+music?.musicName)
            bottomSheetAddPlayListFrag.musicCurent=(music)
            bottomSheetAddPlayListFrag.show(childFragmentManager, null)
            Timber.e("ltnghia"+music?.musicName)

        }
    }

    private fun initData() {}
}