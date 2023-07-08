package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.bottomsheetsort

import android.view.View
import com.example.tfmmusic.R

class BottomSheetSortFragment :
    BaseBottomSheetDialogFragment<BottomsheetSortBinding?, BottomSheetSortViewModel?>() {
    private var musicList: List<Music> = ArrayList<Music>()
    fun getMusicList(): List<Music> {
        return musicList
    }

    fun setMusicList(musicList: List<Music>) {
        this.musicList = musicList
    }

    protected val viewModel: Class<BottomSheetSortViewModel>
        protected get() = BottomSheetSortViewModel::class.java
    val layoutId: Int
        get() = R.layout.bottomsheet_sort

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initListener()
    }

    private fun initListener() {
        binding.framelayout.setOnClickListener { v -> dismiss() }
        binding.byTheTime.setOnClickListener { v ->
            EventBus.getDefault().post(MessageEvent(Constant.SORT_TIME, musicList))
            dismiss()
        }
        binding.byTheName.setOnClickListener { v ->
            EventBus.getDefault().post(MessageEvent(Constant.SORT_NAME, musicList))
            dismiss()
        }
    }
}