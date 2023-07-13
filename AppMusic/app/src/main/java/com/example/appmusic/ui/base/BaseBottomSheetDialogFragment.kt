package com.example.appmusic.ui.base

import android.app.Dialogimport

android.os.Bundleimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport android.widget.FrameLayoutimport androidx.databinding.DataBindingUtilimport androidx.databinding.ViewDataBindingimport androidx.lifecycle.ViewModelProviderimport com.example.appmusic.Rimport com.google.android.material.bottomsheet.BottomSheetBehaviorimport com.google.android.material.bottomsheet.BottomSheetDialogimport com.google.android.material.bottomsheet.BottomSheetDialogFragment
abstract class BaseBottomSheetDialogFragment<B : ViewDataBinding?, T : BaseViewModel?> :
    BottomSheetDialogFragment() {
    var binding: B? = null
    var viewModel: T? = null
    protected abstract fun getViewModel(): Class<T>?
    abstract val layoutId: Int
    protected abstract fun onCreatedView(view: View?, savedInstanceState: Bundle?)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewModel = ViewModelProvider(this).get<T>(getViewModel())
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get<T>(getViewModel())
        onCreatedView(view, savedInstanceState)
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<FrameLayout>(androidx.navigation.ui.R.id.design_bottom_sheet)
        val layoutParams = bottomSheet!!.layoutParams
        if (layoutParams != null) {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        bottomSheet.layoutParams = layoutParams
        BottomSheetBehavior.from(bottomSheet as View?).state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomCreateStoryStyle)
    }

    override fun onStop() {
        super.onStop()
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setWindowAnimations(-1)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
            //                dialogInterface.behavior.setDraggable(false);
            dialogInterface.behavior.isDraggable = false
        }
        return dialog
    }
}