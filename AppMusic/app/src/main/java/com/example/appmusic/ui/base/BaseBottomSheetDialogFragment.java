package com.example.appmusic.ui.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appmusic.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;


public abstract class BaseBottomSheetDialogFragment<B extends ViewDataBinding, T extends BaseViewModel> extends BottomSheetDialogFragment {

    public B binding;
    public T viewModel;
    protected abstract Class<T> getViewModel();

    public abstract int getLayoutId();
    protected abstract void onCreatedView(View view, Bundle savedInstanceState);


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        viewModel = new ViewModelProvider(this).get(getViewModel());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(getViewModel());
        onCreatedView(view, savedInstanceState);
    }
    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(androidx.navigation.ui.R.id.design_bottom_sheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        bottomSheet.setLayoutParams(layoutParams);
        BottomSheetBehavior.from((View)bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomCreateStoryStyle);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setWindowAnimations(-1);
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupFullHeight(bottomSheetDialog);
//                dialogInterface.behavior.setDraggable(false);
                ((BottomSheetDialog) dialogInterface).getBehavior().setDraggable(false);
            }
        });
        return dialog;
    }

}
