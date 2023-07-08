package com.example.appmusic.ui.main.home.video.videosearch

import android.view.View
import com.example.tfmmusic.R
import java.util.Collections

class VideoSearchFragment :
    BaseBindingFragment<FragmentSearchVideoBinding?, VideoSearchViewmodel?>() {
    private var listRecent: List<VideoRecent> = ArrayList<VideoRecent>()
    private val listHistorySearch: MutableList<HistorySearch?> = ArrayList<HistorySearch?>()
    private var videoAdapter: ItemVideoAdapter? = null
    private var itemHistorySearchAdapter: historySearchAdapter? = null
    protected val viewModel: Class<VideoSearchViewmodel>
        protected get() = VideoSearchViewmodel::class.java
    protected val layoutId: Int
        protected get() = R.layout.fragment_search_video

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initListener()
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        videoAdapter = ItemVideoAdapter()
        videoAdapter.setIcLickItemSuggestionVideo { position, videoRecent ->
            mainViewModel.itemPlayVideoMutableLiveData.postValue(videoRecent)
            val historySearch = HistorySearch(binding.edtSearch.getText().toString())
            var check = false
            if (listHistorySearch.size > 0) {
                for (i in listHistorySearch.indices) {
                    if (binding.edtSearch.getText().toString()
                            .equals(listHistorySearch[i].getTextSeacrch())
                    ) {
                        check = true
                        break
                    }
                }
                if (!check) {
                    viewModel.inserHistorySearch(historySearch)
                } else {
//                    listHistorySearch.set(listHistorySearch.size() - 1, historySearch);
                    Collections.reverse(listHistorySearch)
                    itemHistorySearchAdapter.setListHistorySearch(listHistorySearch)
                }
            } else {
                viewModel.inserHistorySearch(historySearch)
            }
            (requireActivity() as MainActivity).navController.navigate(R.id.fragment_detailVideoYoutube)
        }
        binding.rcListResearch.setAdapter(videoAdapter)
        itemHistorySearchAdapter = historySearchAdapter()
        binding.rcListHistorySearch.setAdapter(itemHistorySearchAdapter)
        itemHistorySearchAdapter.setiBaseClickAdapter { position ->
            viewModel.getVideoHome(
                listHistorySearch[position].getTextSeacrch()
            )
        }
    }

    private fun initData() {
        viewModel.getAllHistorySearch()
        viewModel.historySearchLiveData.observe(getViewLifecycleOwner()) { historySearches ->
            if (historySearches != null) {
                listHistorySearch.clear()
                listHistorySearch.addAll(historySearches)
                Collections.reverse(listHistorySearch)
                itemHistorySearchAdapter.setListHistorySearch(listHistorySearch)
            }
        }
        viewModel.videoSearchLiveDataVN.observe(getViewLifecycleOwner()) { videoYoutube ->
            listRecent = VideoRecent.getListVideoRecent(videoYoutube)
            videoAdapter.setVideoYoutube(listRecent)
        }
    }

    private fun initListener() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.getVideoHome(binding.edtSearch.getText().toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.imBack.setOnClickListener { v -> (requireActivity() as MainActivity).navController.popBackStack() }
    }
}