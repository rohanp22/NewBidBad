package com.example.bidbadnew.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bidbadnew.ActionBottomDialogFragment;
import com.example.bidbadnew.Adapter.ProductAdapter1;
import com.example.bidbadnew.Fragments.AccessoriesFragment;
import com.example.bidbadnew.Fragments.ApparelFragment;
import com.example.bidbadnew.Fragments.ApplicancesFragment;
import com.example.bidbadnew.Fragments.ElectronicsFragment;
import com.example.bidbadnew.Fragments.FitnessFragment;
import com.example.bidbadnew.Fragments.HomeFurnitureFragment;
import com.example.bidbadnew.Fragments.OthersFragment;
import com.example.bidbadnew.Fragments.PersonalCareFragment;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Others.CustomViewPager;
import com.example.bidbadnew.R;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class HomeFragment extends Fragment implements ActionBottomDialogFragment.ItemClickListener {

    private HomeViewModel homeViewModel;
    ViewPager viewPager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.home_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("Itemclicked", item.getItemId() + "");
                switch (item.getItemId()) {
                    case R.id.navigation_wallet:
                        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_walletFragment);
                        return true;

                    case R.id.navigation_notification:
                        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_notificationsFragment);
                        return true;

                    default:
                        break;
                }

                return true;
            }
        });
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerCategories);

        tabLayout.addTab(tabLayout.newTab().setText("Electronics"));
        tabLayout.addTab(tabLayout.newTab().setText("Appliances"));
        tabLayout.addTab(tabLayout.newTab().setText("Accessories"));
        tabLayout.addTab(tabLayout.newTab().setText("Personal Care"));
        tabLayout.addTab(tabLayout.newTab().setText("Home & Furniture"));
        tabLayout.addTab(tabLayout.newTab().setText("Fitness"));
        tabLayout.addTab(tabLayout.newTab().setText("Others"));
        tabLayout.addTab(tabLayout.newTab().setText("Apparel"));

        MyAdapter adapter = new MyAdapter(view.getContext(), getChildFragmentManager(), tabLayout.getTabCount());
        Log.d("Tabcount: ", tabLayout.getTabCount()+"");
        viewPager.setAdapter(adapter);
        viewPager.requestDisallowInterceptTouchEvent(false);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("TabSelected", tab.getPosition() + "");
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bar).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
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
                    return new ElectronicsFragment();

                case 1:
                    return new ApplicancesFragment();

                case 2:
                    return new AccessoriesFragment();

                case 3:
                    return new ElectronicsFragment();

                case 4:
                    return new HomeFurnitureFragment();

                case 5:
                    return new FitnessFragment();

                case 6:
                    return new OthersFragment();

                case 7:
                    return new ApparelFragment();

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

    @Override
    public void onItemClick(String item) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    class sortTime implements Comparator<Current_Product> {
        public int compare(Current_Product a, Current_Product b) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date a1 = null, b1 = null;
            try {
                a1 = simpleDateFormat.parse(a.getEndtime());
                b1 = simpleDateFormat.parse(b.getEndtime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return a1.compareTo(b1);
        }
    }
}