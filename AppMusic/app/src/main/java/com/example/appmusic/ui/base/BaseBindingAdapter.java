package com.example.appmusic.ui.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BaseBindingAdapter<B extends ViewDataBinding> extends RecyclerView.Adapter<BaseBindingAdapter.BaseHolder<B>> {

    protected IBaseClickAdapter iBaseClickAdapter;

    public void setiBaseClickAdapter(IBaseClickAdapter iBaseClickAdapter) {
        this.iBaseClickAdapter = iBaseClickAdapter;
    }

    protected abstract void onBindViewHolderBase(@NonNull BaseHolder<B> holder, int position);

    protected abstract int getLayoutIdItem();

    protected abstract int getSizeItem();

    @NonNull
    @Override
    public BaseHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutIdItem(), parent, false);

        return new BaseHolder<B>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<B> holder, int position) {
        holder.itemView.setOnClickListener(view -> {
            iBaseClickAdapter.clickItem(holder.getAdapterPosition());
        });
        onBindViewHolderBase(holder, holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return getSizeItem();
    }

    public static class BaseHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public B binding;

        public BaseHolder(@NonNull B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public interface IBaseClickAdapter {
        void clickItem(int position);
    }
}
