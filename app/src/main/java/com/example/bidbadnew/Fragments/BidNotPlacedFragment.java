package com.example.bidbadnew.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bidbadnew.Activities.MainActivity;
import com.example.bidbadnew.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class BidNotPlacedFragment extends BottomSheetDialogFragment {


    public BidNotPlacedFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bid_not_placed, container, false);
        MaterialButton materialButton = view.findViewById(R.id.materialButton);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).navController.navigate(R.id.action_bidNotPlacedFragment_to_addMoneyFragment);
            }
        });

        return view;
    }
}