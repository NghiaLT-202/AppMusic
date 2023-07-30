package com.example.appmusic.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.appmusic.R
import com.example.appmusic.databinding.CustomDialogOptionPlaylistBinding
import com.example.appmusic.ui.base.BaseBindingDialogFragment
import com.example.appmusic.ui.main.MainViewModel
import java.util.Objects

class OptionPlayListDialog :
    BaseBindingDialogFragment<CustomDialogOptionPlaylistBinding, MainViewModel>() {
    private var posX = 0f
    private var posY = 0f
    private var iOptionCollectionDialog: IOptionCollectionDialog? = null
    fun setiOptionCollectionDialog(iOptionCollectionDialog: IOptionCollectionDialog?) {
        this.iOptionCollectionDialog = iOptionCollectionDialog
    }

    override val layoutId: Int
        get() = R.layout.custom_dialog_option_playlist

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
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
        binding.tvEdit.setOnClickListener { v ->
            preventTwoClick(v, 500)
            iOptionCollectionDialog!!.edit()
            dismiss()
        }
        binding.tvDelete.setOnClickListener { v ->
            preventTwoClick(v, 500)
            iOptionCollectionDialog!!.delete()
            dismiss()
        }
        binding.view.setOnClickListener { Objects.requireNonNull(dialog)?.dismiss() }
    }

    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }


    fun showDialog(
        posX: Float,
        posY: Float,
        fragmentManager: FragmentManager
    ) {
        this.posX = posX
        this.posY = posY
        show(fragmentManager, null)
    }
    private fun initLayoutParam() {
        Objects.requireNonNull(dialog)?.setCancelable(true)
        dialog!!.window!!.decorView.post {
            if (isAdded) {
                binding.rootContainer.x = posX - binding.rootContainer.width
                if (binding.rootContainer.x < 0) {
                    binding.rootContainer.x = 0f
                }
                binding.rootContainer.y = posY
            }
        }
    }

    interface IOptionCollectionDialog {
        fun delete()
        fun edit()
    }
}