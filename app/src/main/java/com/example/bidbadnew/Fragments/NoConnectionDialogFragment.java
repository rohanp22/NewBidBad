package com.example.bidbadnew.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bidbadnew.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NoConnectionDialogFragment extends BottomSheetDialogFragment {

    public static final String TAG = "NoConnectionDialogFragment";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nointernet, container, false);
        this.setCancelable(false);
        return view;
    }

    public static NoConnectionDialogFragment newInstance() {

        Bundle args = new Bundle();

        NoConnectionDialogFragment fragment = new NoConnectionDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
