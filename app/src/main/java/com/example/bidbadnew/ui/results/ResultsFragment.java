package com.example.bidbadnew.ui.results;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.bidbadnew.Fragments.AllBidsFragment;
import com.example.bidbadnew.Fragments.MyBidsFragment;
import com.example.bidbadnew.Fragments.OngoingBidsFragment;
import com.example.bidbadnew.R;
import com.google.android.material.tabs.TabLayout;

public class ResultsFragment extends Fragment implements AllBidsFragment.AllBidsFragmentListener {

    private ResultsViewModel dashboardViewModel;
    ViewPager viewPager;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(ResultsViewModel.class);
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        viewPager=(ViewPager) root.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Ongoing bids"));
        tabLayout.addTab(tabLayout.newTab().setText("My Bids"));
        tabLayout.addTab(tabLayout.newTab().setText("All Bids"));

        MyAdapter adapter = new MyAdapter(root.getContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffbc00"));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    @Override
    public void onItemClick() {
        Navigation.findNavController(root).navigate(R.id.action_navigation_dashboard_to_productDescriptionHistory);
    }

    class MyAdapter extends FragmentPagerAdapter {

        int totalTabs;

        public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);
            this.totalTabs = totalTabs;
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new OngoingBidsFragment();

                case 1:
                    return new MyBidsFragment("mybids");

                case 2:
                    return new AllBidsFragment(ResultsFragment.this);

                default:
                    return null;
            }
        }
        // this counts total number of tabs
        @Override
        public int getCount() {
            return totalTabs;
        }
    }
}