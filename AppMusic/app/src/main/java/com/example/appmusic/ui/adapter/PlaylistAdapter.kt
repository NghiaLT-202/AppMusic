package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.databinding.ItemPlayListBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class PlaylistAdapter : BaseBindingAdapter<ItemPlayListBinding>() {
     var listPlayList: MutableList<PlayList?> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var iclickMenu: IclickMenu? = null
    var totalSong = 0
    fun setIclickMenu(iclickMenu: IclickMenu?) {
        this.iclickMenu = iclickMenu
    }



    override fun onBindViewHolderBase(holder: BaseHolder<ItemPlayListBinding>, position: Int) {
        holder.binding.imImagePlaylist.setImageResource(R.drawable.buoc_qua_nhau)
        holder.binding.tvNamePlayList.text = listPlayList[position]?.namePlayList
        //        totalSong=getTotalSong();
//        holder.binding.tvTotalSong.setText(totalSong);
        holder.binding.imMore.setOnClickListener { v ->
            val location = IntArray(2)
            holder.binding.locationShowDialog.getLocationInWindow(location)
            iclickMenu!!.clickMenu(location, v, listPlayList[position], position)
        }
        holder.itemView.setOnClickListener { iclickMenu!!.clickItem(holder.adapterPosition) }
    }

    interface IclickMenu {
        fun clickMenu(location: IntArray, view: View?, playList: PlayList?, position: Int)
        fun clickItem(position: Int)
    }

    override fun getLayoutIdItem(): Int {
        return R.layout.item_play_list
    }

    override fun getSizeItem(): Int {
        return listPlayList.size
    }
}