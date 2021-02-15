package com.example.bidbadnew.ui.results;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.bidbadnew.Fragments.AllBidsFragment;
import com.example.bidbadnew.Fragments.MyBidsFragment;
import com.example.bidbadnew.Fragments.OngoingBidsFragment;
import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Model.OngoingItems;
import com.example.bidbadnew.Model.PastProducts;
import com.example.bidbadnew.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class ResultsFragment extends Fragment implements AllBidsFragment.AllBidsFragmentListener, MyBidsFragment.MyBidsFragmentListener, OngoingBidsFragment.OnGoingFragmentListener {

    private ResultsViewModel dashboardViewModel;
    ViewPager viewPager;
    View root;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getActivity().findViewById(R.id.resultsDot).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.personDot).setVisibility(View.INVISIBLE);
        ((FloatingActionButton) getActivity().findViewById(R.id.fabhome)).setImageDrawable(getResources().getDrawable(R.drawable.home_indicator));

        dashboardViewModel =
                new ViewModelProvider(this).get(ResultsViewModel.class);
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        viewPager=(ViewPager) root.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Current bids"));
        tabLayout.addTab(tabLayout.newTab().setText("My Bids"));
        tabLayout.addTab(tabLayout.newTab().setText("Our history"));

        MyAdapter adapter = new MyAdapter(root.getContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorSecondary));
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
    public void onItemClick(PastProducts pastProducts) {
        Navigation.findNavController(root).navigate(ResultsFragmentDirections.actionNavigationDashboardToProductDescriptionHistory(pastProducts));
    }

    @Override
    public void onItemClick(MyBid pastProducts) {
        Navigation.findNavController(root).navigate(ResultsFragmentDirections.actionNavigationDashboardToProductDescriptionHistoryMyBids(pastProducts));
    }

    @Override
    public void onItemClick(OngoingItems pastProducts) {
        Navigation.findNavController(root).navigate(ResultsFragmentDirections.actionNavigationDashboardToProductDescriptionOngoing(pastProducts));
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
                    return new OngoingBidsFragment(ResultsFragment.this);

                case 1:
                    return new MyBidsFragment(ResultsFragment.this);

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