package edu.skku.cs.mysimplecalendar.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.activity.main.CategoryChip;
import edu.skku.cs.mysimplecalendar.databinding.FragMainBottomBinding;

public class NewsScrapDialog extends BottomSheetDialogFragment {

    FragMainBottomBinding bind;

    CategoryPointer categoryPointer = new CategoryPointer();

    public OnScrap onScrap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.frag_main_bottom,container,false);
        return bind.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * Do binding
         *
         */



        new CategoryChip(getContext(),bind.cgCategory, categoryPointer);

        bind.btScrap.setOnClickListener(v->{
            if(categoryPointer.category.isEmpty()) return;
            onScrap.onScrap(categoryPointer.category);
            dismiss();
        });
    }
    public class CategoryPointer{
        public String category = "";
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog mDialog = super.onCreateDialog(savedInstanceState);
        mDialog.setOnShowListener(dialog->{
          //setCancelable(false);
          FrameLayout layout = ((FrameLayout)((BottomSheetDialog) dialog).findViewById(com.google.android.material.R.id.design_bottom_sheet));
            BottomSheetBehavior behavior = BottomSheetBehavior.from(layout);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setDraggable(false);
        });

        return mDialog;
    }

    public interface OnScrap{
        void onScrap(String category);
    }
}
