package com.example.appmusic.ui.main.home.mymusic.searchfragment

import android.view.View
import com.example.tfmmusic.App
import java.util.Locale

class SearchFragment : BaseBindingFragment<FragmentResearchBinding?, SearchViewModel?>() {
    private val musicList: MutableList<Music> = ArrayList<Music>()
    var researchAdapter: ResearchAdapter? = null
    val layoutId: Int
        get() = R.layout.fragment_research
    protected val viewModel: Class<SearchViewModel>
        protected get() = SearchViewModel::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        initListener()
        initAdapter()
    }

    private fun initListener() {
        binding.imBack.setOnClickListener { v -> (requireActivity() as MainActivity).navController.popBackStack() }
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val research: String = binding.edtSearch.getText().toString()
                val listSearch: ArrayList<Music> = ArrayList<Music>()
                for (i in musicList.indices) {
                    if (musicList[i].getMusicName().toLowerCase().trim()
                            .contains(research.lowercase(
                                Locale.getDefault()
                            ).trim { it <= ' ' })
                    ) {
                        listSearch.add(musicList[i])
                    }
                }
                researchAdapter.setArrayList(listSearch)
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun initAdapter() {
        researchAdapter = ResearchAdapter()
        binding.rcListResearch.setAdapter(researchAdapter)
        researchAdapter.setiBaseClickAdapter { position ->
            App.getInstance().setMusicCurrent(musicList[position])
            (requireActivity() as MainActivity).navController.navigate(R.id.fragment_detail_music)
        }
    }

    private fun initData() {
        viewModel.getAllMusicSearch(getContext())
        viewModel.listMusic.observe(getViewLifecycleOwner()) { music ->
            musicList.clear()
            musicList.addAll(music)
            researchAdapter.setArrayList(musicList)
        }
    }
}