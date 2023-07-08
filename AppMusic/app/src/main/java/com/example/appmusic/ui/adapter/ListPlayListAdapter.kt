package com.example.appmusic.ui.adapter

import com.example.tfmmusic.R

class ListPlayListAdapter : BaseBindingAdapter<ItemListPlayListBinding?>() {
    private val listPlay: MutableList<PlayList> = ArrayList<PlayList>()
    fun setListPlay(listPlay: List<PlayList>?) {
        this.listPlay.clear()
        this.listPlay.addAll(listPlay!!)
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(
        holder: BaseHolder<ItemListPlayListBinding?>,
        position: Int
    ) {
        holder.binding.tvNamePlayList.setText(listPlay[position].getNamePlayList())
        holder.itemView.setOnClickListener { v -> iBaseClickAdapter.clickItem(holder.getAdapterPosition()) }
    }

    protected fun getLayoutIdItem(): Int {
        return R.layout.item_list_play_list
    }

    protected fun getSizeItem(): Int {
        return listPlay.size
    }
}