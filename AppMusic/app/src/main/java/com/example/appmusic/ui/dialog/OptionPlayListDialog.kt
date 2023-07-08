package com.example.appmusic.ui.dialog

import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.tfmmusic.R
import java.util.Objects

class OptionPlayListDialog :
    BaseBindingDialogFragment<CustomDialogOptionPlaylistBinding?, MainViewModel?>() {
    private var posX = 0f
    private var posY = 0f
    private var iOptionCollectionDialog: IOptionCollectionDialog? = null
    fun setiOptionCollectionDialog(iOptionCollectionDialog: IOptionCollectionDialog?) {
        this.iOptionCollectionDialog = iOptionCollectionDialog
    }

    fun getLayoutId(): Int {
        return R.layout.custom_dialog_option_playlist
    }

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initLayoutParam()
        initListener()
    }

    fun preventTwoClick(view: View, time: Int) {
        if (view.isAttachedToWindow) {
            view.isEnabled = false
            view.postDelayed({ view.isEnabled = true }, time.toLong())
        }
    }

    private fun initListener() {
        binding.tvEdit.setOnClickListener(View.OnClickListener { v ->
            preventTwoClick(v, 500)
            iOptionCollectionDialog!!.edit()
            dismiss()
        })
        binding.tvDelete.setOnClickListener(View.OnClickListener { v ->
            preventTwoClick(v, 500)
            iOptionCollectionDialog!!.delete()
            dismiss()
        })
        binding.view.setOnClickListener(View.OnClickListener {
            Objects.requireNonNull(getDialog()).dismiss()
        })
    }

    protected fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    fun showDialog(
        posX: Float,
        posY: Float,
        fragmentManager: FragmentManager?
    ) {
        this.posX = posX
        this.posY = posY
        show(fragmentManager, null)
    }

    private fun initLayoutParam() {
        Objects.requireNonNull(getDialog()).setCancelable(true)
        getDialog().getWindow().getDecorView().post(Runnable {
            if (isAdded()) {
                binding.rootContainer.setX(posX - binding.rootContainer.getWidth())
                if (binding.rootContainer.getX() < 0) {
                    binding.rootContainer.setX(0f)
                }
                binding.rootContainer.setY(posY)
            }
        })
    }

    interface IOptionCollectionDialog {
        fun delete()
        fun edit()
    }
}