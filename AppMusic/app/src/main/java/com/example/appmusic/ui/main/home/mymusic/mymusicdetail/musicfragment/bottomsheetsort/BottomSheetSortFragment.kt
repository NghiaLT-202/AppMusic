package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.bottomsheetsort

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.BottomsheetSortBinding
import com.example.appmusic.ui.base.BaseBottomSheetDialogFragment
import org.greenrobot.eventbus.EventBus

class BottomSheetSortFragment :
    BaseBottomSheetDialogFragment<BottomsheetSortBinding?, BottomSheetSortViewModel>() {
    var musicList: MutableList<Music?> = mutableListOf()
    override fun getViewModel(): Class<BottomSheetSortViewModel> {
        return BottomSheetSortViewModel::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.bottomsheet_sort
    }



    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initListener()
    }

    private fun initListener() {
        binding!!.framelayout.setOnClickListener { v: View? -> dismiss() }
        binding!!.byTheTime.setOnClickListener { v: View? ->
            EventBus.getDefault().post(MessageEvent(Constant.SORT_TIME, musicList))
            dismiss()
        }
        binding!!.byTheName.setOnClickListener { v: View? ->
            EventBus.getDefault().post(MessageEvent(Constant.SORT_NAME, musicList))
            dismiss()
        }
    }
}