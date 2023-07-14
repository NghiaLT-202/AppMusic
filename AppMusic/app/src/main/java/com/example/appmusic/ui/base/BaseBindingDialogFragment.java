package com.example.appmusic.ui.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;


public abstract class BaseBindingDialogFragment<B extends ViewDataBinding, V extends BaseViewModel> extends BaseDialogFragment {
    public B binding;
    public V viewModel;
    public abstract int getLayoutId();
    protected abstract void onCreatedView(View view, Bundle savedInstanceState);
    public Toast toast = null;

    @SuppressLint("Showtoast")
    public void toast(String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        this.toast.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(getViewModel());
    }

    protected abstract Class<V> getViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onCreatedView(view,savedInstanceState);
    }
}
