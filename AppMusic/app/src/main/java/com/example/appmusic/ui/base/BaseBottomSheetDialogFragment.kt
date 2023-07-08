package com.example.appmusic.ui.base

import android.app.Dialog
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment

abstract class BaseBottomSheetDialogFragment<B : ViewDataBinding?, T : BaseViewModel?> :
    BottomSheetDialogFragment() {
    var binding: B? = null
    var viewModel: T? = null
    protected abstract fun getViewModel(): Class<T>?
    abstract fun getLayoutId(): Int
    protected abstract fun onCreatedView(view: View?, savedInstanceState: Bundle?)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        viewModel = ViewModelProvider(this).get<T>(getViewModel())
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get<T>(getViewModel())
        onCreatedView(view, savedInstanceState)
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet: FrameLayout =
            bottomSheetDialog.findViewById<FrameLayout>(androidx.navigation.ui.R.id.design_bottom_sheet)
        val layoutParams: ViewGroup.LayoutParams = bottomSheet.getLayoutParams()
        if (layoutParams != null) {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        bottomSheet.setLayoutParams(layoutParams)
        BottomSheetBehavior.from<View>(bottomSheet as View)
            .setState(BottomSheetBehavior.STATE_EXPANDED)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomCreateStoryStyle)
    }

    override fun onStop() {
        super.onStop()
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setWindowAnimations(-1)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener(object : OnShowListener {
            override fun onShow(dialogInterface: DialogInterface) {
                val bottomSheetDialog: BottomSheetDialog = dialogInterface as BottomSheetDialog
                setupFullHeight(bottomSheetDialog)
                //                dialogInterface.behavior.setDraggable(false);
                (dialogInterface as BottomSheetDialog).getBehavior().setDraggable(false)
            }
        })
        return dialog
    }
}