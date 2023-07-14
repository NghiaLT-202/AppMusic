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
import com.example.appmusic.ui.main.MainViewModel;

public abstract class BaseBindingFragment<B extends ViewDataBinding, T extends BaseViewModel> extends BaseFragment {
    public B binding;
    public T viewModel;
    public MainViewModel mainViewModel;
    protected abstract Class<T> getViewModel();
    protected abstract int getLayoutId();
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
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel = new ViewModelProvider(this).get(getViewModel());
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onCreatedView(view, savedInstanceState);
    }
}
