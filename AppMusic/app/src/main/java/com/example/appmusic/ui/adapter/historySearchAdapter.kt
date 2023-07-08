package com.example.appmusic.ui.adapter

import com.example.tfmmusic.R

class historySearchAdapter : BaseBindingAdapter<ItemHistorySearchBinding?>() {
    private val listHistorySearch: MutableList<HistorySearch> = ArrayList<HistorySearch>()
    fun setListHistorySearch(listHistorySearch: List<HistorySearch>?) {
        this.listHistorySearch.clear()
        this.listHistorySearch.addAll(listHistorySearch!!)
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(
        holder: BaseHolder<ItemHistorySearchBinding?>,
        position: Int
    ) {
        holder.binding.tvHistorySearch.setText(listHistorySearch[holder.getAdapterPosition()].getTextSeacrch())
    }

    protected fun getLayoutIdItem(): Int {
        return R.layout.item_history_search
    }

    protected fun getSizeItem(): Int {
        return listHistorySearch.size
    }
}