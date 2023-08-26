package com.example.appmusic.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.appmusic.data.model.DataMusic

abstract class BaseBindingDialogFragment<B : ViewDataBinding, V : BaseViewModel> :
    BaseDialogFragment() {
    lateinit var binding: B
    lateinit var viewModel: V
    abstract val layoutId: Int
    var delete : () -> Unit = {  }
    var edit : () -> Unit = {  }

    protected abstract fun onCreatedView(view: View?, savedInstanceState: Bundle?)
    var toast: Toast? = null
    @SuppressLint("Showtoast")
    fun toast(text: String?) {
        if (toast != null) {
            toast!!.cancel()
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get<V>(getViewModel())
    }

    protected abstract fun getViewModel(): Class<V>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreatedView(view, savedInstanceState)
    }
}