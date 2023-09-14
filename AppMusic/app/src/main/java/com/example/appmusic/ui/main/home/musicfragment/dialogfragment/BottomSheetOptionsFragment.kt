package com.example.appmusic.ui.main.home.musicfragment.dialogfragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.BottomSheetListFuntionBinding
import com.example.appmusic.ui.base.BaseBottomSheetDialogFragment
import com.example.appmusic.ui.main.home.musicfragment.dialogfragment.dialog.BottomSheetAddPlayListFrag

class BottomSheetOptionsFragment :
    BaseBottomSheetDialogFragment<BottomSheetListFuntionBinding, BottomSheetOptionsViewmodel>() {
    var dataMusic: DataMusic? = null
    var namePlayList: String? = null
    private val bottomSheetAddPlayListFrag : BottomSheetAddPlayListFrag by lazy { BottomSheetAddPlayListFrag() }

    override fun getViewModel(): Class<BottomSheetOptionsViewmodel> {
        return BottomSheetOptionsViewmodel::class.java
    }

    override val layoutId: Int
        get() = R.layout.bottom_sheet_list_funtion

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initListener()
    }

    private fun initListener() {
        binding.framelayout.setOnClickListener { dismiss() }
        binding.addToPlayList.setOnClickListener {
            bottomSheetAddPlayListFrag.dataMusicCurrent = (dataMusic)
            bottomSheetAddPlayListFrag.show(childFragmentManager, null)
        }
    }

}