package com.example.appmusic.ui.main.home.mymusic.playlistfragment.dialogFragmentAddPlayList

import android.view.View
import com.example.tfmmusic.R

class DialogAddPlayListFragment :
    BaseBindingDialogFragment<DialogAddPlayListBinding?, DialogAddPlayListVM?>() {
    private val listPlayList: MutableList<PlayList> = ArrayList<PlayList>()
    private var iDialogAdd: IDialogAdd? = null
    private var text: String? = null
    private var textEdit: String? = null
    fun setTextEdit(textEdit: String?) {
        this.textEdit = textEdit
    }

    fun setText(text: String?) {
        this.text = text
    }

    fun setiDialogAdd(iDialogAdd: IDialogAdd?) {
        this.iDialogAdd = iDialogAdd
    }

    val layoutId: Int
        get() = R.layout.dialog_add_play_list
    protected val viewModel: Class<DialogAddPlayListVM>
        protected get() = DialogAddPlayListVM::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initView()
        initListener()
        initData()
    }

    private fun initView() {
        binding.tvName.setText(text)
    }

    private fun initListener() {
        binding.btnCancle.setOnClickListener { v -> dismiss() }
        binding.btnOk.setOnClickListener { v ->
            iDialogAdd!!.ok(
                binding.edtInputPlayList.getText().toString()
            )
        }
        binding.edtInputPlayList.setHint(textEdit)
    }

    private fun initData() {
        viewModel.getAllPlayList()
        viewModel.listPlayList.observe(getViewLifecycleOwner()) { playLists ->
            listPlayList.clear()
            listPlayList.addAll(playLists)
        }
    }

    interface IDialogAdd {
        fun cancle(position: Int)
        fun ok(inputName: String?)
    }
}