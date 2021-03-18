package com.example.bidbadnew.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;

public class ReferFragment extends Fragment {

    TextView share;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);
        View view = inflater.inflate(R.layout.fragment_refer, container, false);

        share = view.findViewById(R.id.referShare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Create an ACTION_SEND Intent*/
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                String shareBody = "Hey! Download the Bidbad app to bid and win exciting items everyday.\nhttp://easyvela.esy.es\n\nAnd use Referral Code " + SharedPrefManager.getInstance(view.getContext()).getUser().getFirstname().toUpperCase()+SharedPrefManager.getInstance(view.getContext()).getUser().getId()+" to get invite bonus";
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share App");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                /*Fire!*/
                view.getContext().startActivity(Intent.createChooser(intent, "Share via"));
            }
        });

        return view;
    }
}
