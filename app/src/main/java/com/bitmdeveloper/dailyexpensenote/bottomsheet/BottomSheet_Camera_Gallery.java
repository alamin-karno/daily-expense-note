package com.bitmdeveloper.dailyexpensenote.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bitmdeveloper.dailyexpensenote.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet_Camera_Gallery extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_camera_gallery,container,false);

        return view;

    }
}
