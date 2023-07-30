package com.example.appmusic.ui.main.home.playlistfragment.dialogFragmentAddPlayList

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.databinding.DialogAddPlayListBinding
import com.example.appmusic.ui.base.BaseBindingDialogFragment

class DialogAddPlayListFragment :
    BaseBindingDialogFragment<DialogAddPlayListBinding, DialogAddPlayListVM>() {
    var listPlayList: MutableList<PlayList> = mutableListOf()
    var iDialogAdd: IDialogAdd? = null
    fun setiDialogAdd(iDialogAdd: IDialogAdd?) {
        this.iDialogAdd = iDialogAdd
    }
    var text: String? = null
    var textEdit: String? = null

    override val layoutId: Int
        get() = R.layout.dialog_add_play_list

    override fun getViewModel(): Class<DialogAddPlayListVM> {
        return DialogAddPlayListVM::class.java
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initView()
        initListener()
        initData()
    }

    private fun initView() {
        binding.tvName.text = text
    }

    private fun initListener() {
        binding.btnCancle.setOnClickListener { v: View? -> dismiss() }
        binding.btnOk.setOnClickListener { v: View? ->
            iDialogAdd!!.ok(
                binding.edtInputPlayList.text.toString()
            )
        }
        binding.edtInputPlayList.hint = textEdit
    }

    private fun initData() {
        viewModel.allPlayList
        viewModel.listPlayList.observe(viewLifecycleOwner) { playLists: MutableList<PlayList> ->
            listPlayList.clear()
            listPlayList.addAll(playLists)
        }
    }

    interface IDialogAdd {
        fun cancle(position: Int)
        fun ok(inputName: String)
    }
}