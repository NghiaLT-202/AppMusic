package com.example.appmusic.ui.main.home.mymusic.searchfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentResearchBinding
import com.example.appmusic.ui.adapter.ResearchAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import java.util.Locale

class SearchFragment : BaseBindingFragment<FragmentResearchBinding?, SearchViewModel>() {
    private val musicList: MutableList<Music?> = ArrayList()
    var researchAdapter: ResearchAdapter? = null


    override fun getViewModel(): Class<SearchViewModel>? {
        return SearchViewModel::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_research
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        initListener()
        initAdapter()
    }

    private fun initListener() {
        binding!!.imBack.setOnClickListener { v: View? -> (requireActivity() as MainActivity).navController!!.popBackStack() }
        binding!!.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val research = binding!!.edtSearch.text.toString()
                val listSearch = ArrayList<Music?>()
                for (i in musicList.indices) {
                    if (musicList[i]?.musicName?.lowercase(Locale.getDefault())
                            ?.trim { it <= ' ' }
                            ?.contains(research.lowercase(Locale.getDefault()).trim { it <= ' ' })!!
                    ) {
                        listSearch.add(musicList[i])
                    }
                }
                researchAdapter?.arrayList=(listSearch)
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun initAdapter() {
        researchAdapter = ResearchAdapter()
        binding!!.rcListResearch.adapter = researchAdapter
        researchAdapter!!.setiBaseClickAdapter { position: Int ->
            App.Companion.instance.musicCurrent=(musicList[position])
            (requireActivity() as MainActivity).navController!!.navigate(R.id.fragment_detail_music)
        }
    }

    private fun initData() {
        viewModel!!.getAllMusicSearch(requireContext())
        viewModel!!.listMusic.observe(viewLifecycleOwner) { music: List<Music?>? ->
            musicList.clear()
            musicList.addAll(music!!)
            researchAdapter?.arrayList=(musicList)
        }
    }
}