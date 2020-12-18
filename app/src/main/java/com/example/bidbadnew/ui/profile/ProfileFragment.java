package com.example.bidbadnew.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel notificationsViewModel;
    public TextView order, cancelled, wonbids, invite, wishlist, payments, howtoplay, feedback, termsandconditions, privacypolicy, support, logout, name;
    public ImageView edit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        order = root.findViewById(R.id.profileOrders);
        cancelled = root.findViewById(R.id.profileCancelled);
        wonbids = root.findViewById(R.id.profileNotifications);
        invite = root.findViewById(R.id.profileInvite);
        wishlist = root.findViewById(R.id.profileWishList);
        payments = root.findViewById(R.id.profileUpi);
        howtoplay = root.findViewById(R.id.profileHowToBid);
        feedback = root.findViewById(R.id.profileSendFeedback);
        termsandconditions = root.findViewById(R.id.profileTermsAndConditions);
        privacypolicy = root.findViewById(R.id.profilePrivacyPolicy);
        support = root.findViewById(R.id.profileSupport);
        logout = root.findViewById(R.id.profileLogout);
        name = root.findViewById(R.id.profileName);
        edit = root.findViewById(R.id.profileEdit);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        wonbids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_notifications_to_navigation_wonbids);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_notifications_to_navigation_order);
            }
        });

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_notifications_to_navigation_refer);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_notifications_to_editProfile);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager
                        .getInstance(root.getContext())
                        .logout();
            }
        });

        return root;
    }
}