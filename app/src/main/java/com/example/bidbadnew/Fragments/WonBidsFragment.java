package com.example.bidbadnew.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bidbadnew.Adapter.WonItemsAdapter;
import com.example.bidbadnew.Model.WonItem;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.google.android.material.appbar.MaterialToolbar;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class WonBidsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<WonItem> wonItems;
    WonItemsAdapter walletAdapter;
    WonItemsViewModel wonViewModel;
    ArrayList<WonItem> sortedList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.fragment_won_items, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        wonViewModel = new ViewModelProvider(this).get(WonItemsViewModel.class);
        wonViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        wonViewModel.getMyWonItems().observe(getViewLifecycleOwner(), new Observer<List<WonItem>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<WonItem> wonItems) {
                wonItems.sort(new sortTime2());
                if(wonItems.size() == 0){
                    view.findViewById(R.id.nowonitems).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.nowonitems).setVisibility(View.GONE);
                }
                walletAdapter = new WonItemsAdapter(view.getContext(), wonItems);
                recyclerView.setAdapter(walletAdapter);
            }
        });
        return view;
    }

    class sortTime2 implements Comparator<WonItem> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(WonItem a, WonItem b) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date a1 = null, b1 = null;
            try {
                a1 = simpleDateFormat.parse(a.getEndtime());
                b1 = simpleDateFormat.parse(b.getEndtime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return b1.compareTo(a1);
        }
    }
}
