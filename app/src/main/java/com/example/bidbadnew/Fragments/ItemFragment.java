package com.example.bidbadnew.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bidbadnew.R;
import com.example.bidbadnew.Fragments.dummy.DummyContent;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.list);
        if (recyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            DummyContent.ITEMS.add(new DummyContent.DummyItem("1", "You begin your bidding journey by first adding BidCoins into your BidCart. ", ""));
            DummyContent.ITEMS.add(new DummyContent.DummyItem("2", "For any bid you place on the items in auction, certain amount of BidCoins will.be deducted from your BidCart as an entry fee. ", ""));
            DummyContent.ITEMS.add(new DummyContent.DummyItem("3", "You will then be allowed to bid ONLY ONCE. ", ""));
            DummyContent.ITEMS.add(new DummyContent.DummyItem("4", "You will be notified if your bid was the highest: you will win the bidding.  ", ""));
            DummyContent.ITEMS.add(new DummyContent.DummyItem("5", "Within 48 hours of winning, you must pay the amount you bid to claim the product.  ", ""));
            DummyContent.ITEMS.add(new DummyContent.DummyItem("6", " In case you fail to do so, the product will be passed on to the second highest bidder. ", ""));
            DummyContent.ITEMS.add(new DummyContent.DummyItem("7", "Through online payment gateways, you can pay for the product and will receive it in due time.  ", ""));
            DummyContent.ITEMS.add(new DummyContent.DummyItem("8", "You are eligible for rewards in the following ways: If you invite your friends to bid, if you bid for twenty products, if the friend you invited bids for 20 products.  ", ""));
            DummyContent.ITEMS.add(new DummyContent.DummyItem("9", "You will be rewarded in free entry, bonuses or discounts.", ""));
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
        }
        return view;
    }
}