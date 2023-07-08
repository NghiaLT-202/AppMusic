package com.example.appmusic.ui.base

import android.view.View
import androidx.databinding.DataBindingUtil

abstract class BaseBindingAdapter<B : ViewDataBinding?> : RecyclerView.Adapter<BaseHolder<B>?>() {
    protected var iBaseClickAdapter: IBaseClickAdapter? = null
    fun setiBaseClickAdapter(iBaseClickAdapter: IBaseClickAdapter?) {
        this.iBaseClickAdapter = iBaseClickAdapter
    }

    protected abstract fun onBindViewHolderBase(holder: BaseHolder<B>, position: Int)
    protected abstract fun getLayoutIdItem(): Int
    protected abstract fun getSizeItem(): Int
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<B> {
        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            getLayoutIdItem(),
            parent,
            false
        )
        return BaseHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder<B>, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener { view: View? ->
            iBaseClickAdapter!!.clickItem(
                holder.getAdapterPosition()
            )
        })
        onBindViewHolderBase(holder, holder.getAdapterPosition())
    }

    override fun getItemCount(): Int {
        return getSizeItem()
    }

    class BaseHolder<B : ViewDataBinding?>(var binding: B) : RecyclerView.ViewHolder(
        binding.getRoot()
    )

    interface IBaseClickAdapter {
        fun clickItem(position: Int)
    }
}