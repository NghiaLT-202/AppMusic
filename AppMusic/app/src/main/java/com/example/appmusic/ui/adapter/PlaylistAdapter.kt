package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.DataPlayList
import com.example.appmusic.databinding.ItemPlayListBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class PlaylistAdapter : BaseBindingAdapter<ItemPlayListBinding>() {
    var listDataPlayList: MutableList<DataPlayList> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var iclickMenu: IclickMenu? = null
    fun setIClickMenu(iClickMenu: IclickMenu) {
        this.iclickMenu = iClickMenu
    }

    var totalSong = 0


    override fun onBindViewHolderBase(holder: BaseHolder<ItemPlayListBinding>, position: Int) {
        listDataPlayList[position].apply {
            with(holder.binding) {
                imImagePlaylist.setImageResource(R.drawable.buoc_qua_nhau)
                tvNamePlayList.text = namePlayList
//                totalSong=totalSong
//        holder.binding.tvTotalSong.setText(totalSong);
                imMore.setOnClickListener { v ->
                    val location = IntArray(2)
                    locationShowDialog.getLocationInWindow(location)
                    iclickMenu!!.clickMenu(location, v, this@apply, position)
                }
            }
        }


        holder.itemView.setOnClickListener { iclickMenu!!.clickItem(holder.adapterPosition) }
    }

    override val layoutIdItem: Int
        get() = R.layout.item_play_list
    override val sizeItem: Int
        get() = listDataPlayList.size

    interface IclickMenu {
        fun clickMenu(location: IntArray, view: View, dataPlayList: DataPlayList, position: Int)
        fun clickItem(position: Int)
    }
}