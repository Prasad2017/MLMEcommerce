package com.mlmecommerce.Fragment.bottomsheet;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mlmecommerce.Extra.Tools;
import com.mlmecommerce.R;


import java.util.Locale;


public class SortSheetDialog extends BottomSheetDialogFragment {

    public static BottomSheetBehavior mBehavior;
    public static AppBarLayout app_bar_layout;
    public static CheckBox relevance, lowToHigh, highToLow, newArrivals, discount, AToZ;

    public static SortSheetDialog newInstance() {
        return new SortSheetDialog();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        final View view = View.inflate(getContext(), R.layout.fragment_sort_sheet_dialog_full, null);

        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        app_bar_layout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);

        relevance = (CheckBox) view.findViewById(R.id.relevance);
        lowToHigh = (CheckBox) view.findViewById(R.id.lowToHigh);
        highToLow = (CheckBox) view.findViewById(R.id.highToLow);
        newArrivals = (CheckBox) view.findViewById(R.id.newArrivals);
        discount = (CheckBox) view.findViewById(R.id.discount);
        AToZ = (CheckBox) view.findViewById(R.id.AToZ);

        ((View) view.findViewById(R.id.lyt_spacer)).setMinimumHeight(Tools.getScreenHeight() / 2);

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                   // showView(app_bar_layout, getActionBarSize());
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                   // showView(app_bar_layout, getActionBarSize());
                }

                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        ((ImageButton) view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void hideView(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);
    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize() {
        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int size = (int) styledAttributes.getDimension(0, 0);
        return size;
    }

}
