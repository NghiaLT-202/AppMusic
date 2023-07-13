package com.example.appmusic.ui.adapter

import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.databinding.ItemListPlayListBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class ListPlayListAdapter : BaseBindingAdapter<ItemListPlayListBinding>() {
    private val listPlay: MutableList<PlayList?> = ArrayList()
    fun setListPlay(listPlay: List<PlayList?>?) {
        this.listPlay.clear()
        this.listPlay.addAll(listPlay!!)
        notifyDataSetChanged()
    }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemListPlayListBinding>, position: Int) {
        holder.binding.tvNamePlayList.text = listPlay[position].getNamePlayList()
        holder.itemView.setOnClickListener { v: View? -> iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
    }

    protected override val layoutIdItem: Int
        protected get() = R.layout.item_list_play_list
    protected override val sizeItem: Int
        protected get() = listPlay.size
}