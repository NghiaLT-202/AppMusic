package com.example.appmusic.ui.main.home.video

import android.view.View
import com.example.tfmmusic.R

class VideoFragment : BaseBindingFragment<FragmentVideoMusicBinding?, VideoViewmodel?>() {
    private val KEY_GET_DATA = 0
    private val KEY_NO_GET_DATA = 1
    private val KEY_CHANGE_QUERY = 2
    private var videoAdapter: VideoAdapter? = null
    private var isLoad = KEY_NO_GET_DATA
    private var listRecent: MutableList<VideoRecent>? = ArrayList<VideoRecent>()
    private val listVideoYoutube: MutableList<VideoRecent> = ArrayList<VideoRecent>()
    val layoutId: Int
        get() = R.layout.fragment_video_music
    protected val viewModel: Class<VideoViewmodel>
        protected get() = VideoViewmodel::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()
    }

    private fun initData() {
        viewModel.getAllRecent()
        viewModel.listRecentVideo.observe(getViewLifecycleOwner()) { videoRecents ->
            listVideoYoutube.clear()
            listVideoYoutube.addAll(videoRecents)
        }
        mainViewModel.getVideoHome(Constant.KEY_SEARCH_HOME)
        mainViewModel.videoTrendingLiveDataVNLoadMore.observe(getViewLifecycleOwner()) { videoYoutube ->
            Timber.e("nghialt: videoTrendingLiveDataVNLoadMore")
            if (videoYoutube != null && videoYoutube.getItems() != null) {
                isLoad = KEY_GET_DATA
                if (listRecent == null || listRecent!!.size == 0) {
                    listRecent = VideoRecent.getListVideoRecent(videoYoutube)
                } else {
                    var i = Math.max(videoYoutube.getItems().size() - 6, 0)
                    while (i < videoYoutube.getItems().size()) {
                        listRecent!!.add(VideoRecent.getVideoRecent(videoYoutube, i))
                        i++
                    }
                }
                videoAdapter.setVideoYoutube(listRecent)
                binding.loading.setVisibility(View.GONE)
            } else {
                isLoad = KEY_CHANGE_QUERY
            }
        }
    }

    private fun initListener() {
        binding.imMenu.setOnClickListener { v -> binding.drawerLayout.openDrawer(GravityCompat.START) }
        binding.navView.setNavigationItemSelectedListener { item ->
            item.setChecked(true)
            binding.drawerLayout.closeDrawers()
            true
        }
        binding.rcListMusicRecentlyPlayed.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (isLoad == KEY_GET_DATA) {
                    isLoad = KEY_NO_GET_DATA
                    mainViewModel.getVideoHomeLoadMore(
                        Constant.KEY_SEARCH_HOME,
                        listRecent!!.size + 5
                    )
                } else if (KEY_CHANGE_QUERY == isLoad) {
                    isLoad = KEY_NO_GET_DATA
                    mainViewModel.getVideoHomeLoadMore(
                        Constant.KEY_SEARCH_HOME_LOAD_MORE,
                        listRecent!!.size + 5
                    )
                }
            }
        })
        binding.tvRecentlyPlayed.setOnClickListener { v ->
            (requireActivity() as MainActivity).navController.navigate(
                R.id.fragment_video_recent
            )
        }
        binding.tvSearch.setOnClickListener { v ->
            (requireActivity() as MainActivity).navController.navigate(
                R.id.fragment_video_search
            )
        }
    }

    private fun initAdapter() {
        videoAdapter = VideoAdapter()
        videoAdapter.setiVideo { position, videoRecent ->
            startService()
            mainViewModel.itemPlayVideoMutableLiveData.postValue(videoRecent)
            insertRecently(videoRecent)
            (requireActivity() as MainActivity).navController.navigate(R.id.fragment_detailVideoYoutube)
        }
        binding.rcListMusicRecentlyPlayed.setAdapter(videoAdapter)
    }

    fun insertRecently(videoRecent: VideoRecent) {
        var checkExistedRecent = false
        for (recent in listVideoYoutube) {
            if (recent.getVideoId().equals(videoRecent.getVideoId())) {
                checkExistedRecent = true
                break
            }
        }
        if (!checkExistedRecent) {
            viewModel.insertVideoRecent(videoRecent)
        }
    }

    private fun startService() {
        val intent = Intent(requireActivity(), YoutubeService::class.java)
        intent.setAction(Constant.STOP_MEDIA_SERVICE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(intent)
        } else {
            requireActivity().startService(intent)
        }
    }
}