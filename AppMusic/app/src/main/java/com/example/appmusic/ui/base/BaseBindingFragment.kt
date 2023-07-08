package com.example.appmusic.ui.base

import android.view.View
import androidx.databinding.DataBindingUtil

abstract class BaseBindingFragment<B : ViewDataBinding?, T : BaseViewModel?> : BaseFragment() {
    var binding: B? = null
    var viewModel: T? = null
    var mainViewModel: MainViewModel? = null
    protected abstract fun getViewModel(): Class<T>?
    protected abstract fun getLayoutId(): Int
    protected abstract fun onCreatedView(view: View?, savedInstanceState: Bundle?)
    var toast: Toast? = null
    @SuppressLint("Showtoast")
    fun toast(text: String?) {
        if (toast != null) {
            toast.cancel()
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(this).get<T>(getViewModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreatedView(view, savedInstanceState)
    }
}