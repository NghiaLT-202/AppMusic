package com.example.appmusic.ui.base

import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseBindingActivity<B : ViewDataBinding, T : BaseViewModel> : BaseActivity() {
    lateinit var binding: B
    lateinit var viewModel: T
    abstract val layoutId: Int
    abstract fun setupView(savedInstanceState: Bundle?)
    abstract fun setupData()
    protected abstract fun getViewModel(): Class<T>
    override fun onCreate(savedInstanceState: Bundle?) {
//        LocaleUtils.applyLocale(this);
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = ViewModelProvider(this).get<T>(getViewModel())
        setupData()
        setupView(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context) {
//        LocaleUtils.applyLocale(newBase);
        super.attachBaseContext(newBase)
    }
}