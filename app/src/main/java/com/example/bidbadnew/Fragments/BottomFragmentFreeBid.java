package com.example.bidbadnew.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.bidbadnew.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomFragmentFreeBid extends BottomSheetDialogFragment
        implements View.OnClickListener {

    public BottomFragmentFreeBid() {

    }

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_freebid, container, false);

        return view;
    }

    int id, bal;
    EditText bidamount;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialog;
    }
}

