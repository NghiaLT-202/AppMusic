package com.example.appmusic.ui.base

import android.annotation.SuppressLintimport

android.os.Bundleimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport android.widget.Toastimport androidx.databinding.DataBindingUtilimport androidx.databinding.ViewDataBindingimport androidx.lifecycle.ViewModelProvider
abstract class BaseBindingDialogFragment<B : ViewDataBinding?, V : BaseViewModel?> :
    BaseDialogFragment() {
    var binding: B? = null
    var viewModel: V? = null
    abstract val layoutId: Int
    protected abstract fun onCreatedView(view: View?, savedInstanceState: Bundle?)
    var toast: Toast? = null
    @SuppressLint("Showtoast")
    fun toast(text: String?) {
        if (toast != null) {
            toast!!.cancel()
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get<V>(getViewModel())
    }

    protected abstract fun getViewModel(): Class<V>?
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreatedView(view, savedInstanceState)
    }
}