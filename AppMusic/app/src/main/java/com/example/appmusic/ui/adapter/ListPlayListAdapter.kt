package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import com.example.appmusic.R
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.databinding.ItemListPlayListBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class ListPlayListAdapter : BaseBindingAdapter<ItemListPlayListBinding>() {
     var listPlay: MutableList<PlayList> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field=value
            notifyDataSetChanged()
        }

     override fun onBindViewHolderBase(
        holder: BaseHolder<ItemListPlayListBinding>,
        position: Int
    ) {
        holder.binding.tvNamePlayList.text = listPlay[position].namePlayList
        holder.itemView.setOnClickListener {
            iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
    }

    override val layoutIdItem: Int
         get() = R.layout.item_list_play_list
    override val sizeItem: Int
         get() = listPlay.size
}