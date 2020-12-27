package com.example.bidbadnew.Fragments;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Notification;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bidbadnew.Adapter.NotificationsAdapter;
import com.example.bidbadnew.Model.NotificationModel;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel mViewModel;
    RecyclerView recyclerView;
    NotificationsAdapter notificationsAdapter;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);
        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.notifications_fragment, container, false);
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        mViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());

        mViewModel.getNotifications().observe(getViewLifecycleOwner(), new Observer<List<NotificationModel>>() {
            @Override
            public void onChanged(List<NotificationModel> notificationModels) {
                notificationsAdapter = new NotificationsAdapter(notificationModels, view.getContext());
                recyclerView.setAdapter(notificationsAdapter);
            }
        });

        return view;
    }
}