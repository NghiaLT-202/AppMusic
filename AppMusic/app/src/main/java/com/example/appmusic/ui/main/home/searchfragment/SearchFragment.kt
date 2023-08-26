package com.example.appmusic.ui.main.home.searchfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.FragmentResearchBinding
import com.example.appmusic.ui.adapter.ResearchAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import java.util.Locale

class SearchFragment : BaseBindingFragment<FragmentResearchBinding, SearchViewModel>() {
    private val dataMusicList: MutableList<DataMusic> = mutableListOf()
    var researchAdapter: ResearchAdapter? = null
    override val layoutId: Int
        get() = R.layout.fragment_research

    override fun getViewModel(): Class<SearchViewModel> {
        return SearchViewModel::class.java
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        initListener()
        initAdapter()
    }

    private fun initListener() {
        binding.imBack.setOnClickListener { (requireActivity() as MainActivity).navController?.popBackStack() }
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val research = binding.edtSearch.text.toString()
                val listSearch = ArrayList<DataMusic>()
                for (i in dataMusicList.indices) {
                    if (dataMusicList[i].musicName.lowercase(Locale.getDefault()).trim { it <= ' ' }
                            .contains(research.lowercase(Locale.getDefault()).trim { it <= ' ' })) {
                        listSearch.add(dataMusicList[i])
                    }
                }
                researchAdapter?.listDataMusic = listSearch
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun initAdapter() {
        researchAdapter = ResearchAdapter()
        binding.rcListResearch.adapter = researchAdapter
        researchAdapter?.itemCount?.let {
            App.instance.musicCurrent = (dataMusicList[it])
            navigateFragment(R.id.fragment_detail_music)
        }

    }

    private fun initData() {
//        viewModel.getAllMusicSearch(context)
        viewModel.listDataMusic.observe(viewLifecycleOwner) {
            dataMusicList.clear()
            dataMusicList.addAll(it)
            researchAdapter?.listDataMusic = it
        }
    }
}