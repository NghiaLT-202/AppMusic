package com.example.appmusic.ui.main.home.video.videorecent

import android.view.View
import com.example.tfmmusic.R

class VideoRecentFragment :
    BaseBindingFragment<FragmentVideoRecentBinding?, VideoRecentViewModel?>() {
    private val recentList: MutableList<VideoRecent> = ArrayList<VideoRecent>()
    private var videoRecentAdapter: VideoRecentAdapter? = null
    protected val viewModel: Class<VideoRecentViewModel>
        protected get() = VideoRecentViewModel::class.java
    protected val layoutId: Int
        protected get() = R.layout.fragment_video_recent

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()
    }

    private fun initListener() {
        binding.imBack.setOnClickListener { v -> (requireActivity() as MainActivity).navController.popBackStack() }
    }

    private fun initAdapter() {
        videoRecentAdapter = VideoRecentAdapter()
        binding.listRecent.setAdapter(videoRecentAdapter)
        videoRecentAdapter.setiVideo { position ->
            val itemPlayVideo = VideoRecent()
            itemPlayVideo.setVideoId(recentList[position].getVideoId())
            mainViewModel.itemPlayVideoMutableLiveData.postValue(itemPlayVideo)
            (requireActivity() as MainActivity).navController.navigate(R.id.fragment_detailVideoYoutube)
        }
    }

    private fun initData() {
        viewModel.getAllRecent()
        viewModel.listRecentVideo.observe(getViewLifecycleOwner()) { videoRecents ->
            recentList.clear()
            recentList.addAll(videoRecents)
            videoRecentAdapter.setVideoRecentList(recentList)
        }
    }
}