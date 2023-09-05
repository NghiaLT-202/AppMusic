package com.example.appmusic.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.ui.base.BaseBindingAdapter.BaseHolder

abstract class BaseBindingAdapter<B : ViewDataBinding> : RecyclerView.Adapter<BaseHolder<B>>() {
    var clickItem: (position: Int, music: DataMusic) -> Unit = { _, _ -> }
    var clickItemView: (position: Int) -> Unit = { }
    var clickMenu: (position: Int, music: DataMusic) -> Unit = { _, _ -> }

    protected abstract fun onBindViewHolderBase(holder: BaseHolder<B>, position: Int)
    protected abstract val layoutIdItem: Int
    protected abstract val sizeItem: Int
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<B> {
        val binding = DataBindingUtil.inflate<B>(
            LayoutInflater.from(parent.context),
            layoutIdItem,
            parent,
            false
        )
        return BaseHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder<B>, position: Int) {
        onBindViewHolderBase(holder, holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return sizeItem
    }

    class BaseHolder<B : ViewDataBinding>(var binding: B) : RecyclerView.ViewHolder(
        binding.root
    )


}