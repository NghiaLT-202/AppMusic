package com.example.appmusic.ui.main.home.musicfragment.bottomsheetsort

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.BottomsheetSortBinding
import com.example.appmusic.ui.base.BaseBottomSheetDialogFragment
import org.greenrobot.eventbus.EventBus

class BottomSheetSortFragment :
    BaseBottomSheetDialogFragment<BottomsheetSortBinding, BottomSheetSortViewModel>() {
    var dataMusicList: MutableList<DataMusic> = mutableListOf()
    override fun getViewModel(): Class<BottomSheetSortViewModel> {
        return BottomSheetSortViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.bottomsheet_sort

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initListener()
    }

    private fun initListener() {
        binding.framelayout.setOnClickListener { dismiss() }
        binding.byTheTime.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(Constant.SORT_TIME, dataMusicList))
            dismiss()
        }
        binding.byTheName.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(Constant.SORT_NAME, dataMusicList))
            dismiss()
        }
    }
}