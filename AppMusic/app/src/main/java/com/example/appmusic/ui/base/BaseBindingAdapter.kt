package com.example.appmusic.ui.base

import android.view.LayoutInflaterimport

android.view.Viewimport android.view.ViewGroupimport androidx.databinding.DataBindingUtilimport androidx.databinding.ViewDataBindingimport androidx.recyclerview.widget.RecyclerViewimport com.example.appmusic.ui.base.BaseBindingAdapter.BaseHolder
abstract class BaseBindingAdapter<B : ViewDataBinding?> : RecyclerView.Adapter<BaseHolder<B>>() {
    protected var iBaseClickAdapter: IBaseClickAdapter? = null
    fun setiBaseClickAdapter(iBaseClickAdapter: IBaseClickAdapter?) {
        this.iBaseClickAdapter = iBaseClickAdapter
    }

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
        holder.itemView.setOnClickListener { view: View? -> iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
        onBindViewHolderBase(holder, holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return sizeItem
    }

    class BaseHolder<B : ViewDataBinding?>(var binding: B) : RecyclerView.ViewHolder(
        binding!!.root
    )

    interface IBaseClickAdapter {
        fun clickItem(position: Int)
    }
}