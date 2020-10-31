package com.example.bidbadnew.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Model.PastProducts;
import com.example.bidbadnew.R;
import com.example.bidbadnew.Others.SharedPrefManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MyBidsFragment extends Fragment {

    ProgressBar progressBar;
    ArrayList<PastProducts> cartItems;
    View view;
    RecyclerView cartList;
    String bid;
    BidHistoryAdapterAll adapterall;
    MyBidsViewModel myBidsViewModel;

    public MyBidsFragment(String bid) {
        this.bid = bid;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bar).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.VISIBLE);
        myBidsViewModel = new ViewModelProvider(this).get(MyBidsViewModel.class);
        final View view = inflater.inflate(R.layout.bid_history_fragments, container, false);

        myBidsViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());

        cartList = view.findViewById(R.id.bidHistoryRecyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        cartList.addItemDecoration(dividerItemDecoration);
        cartList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        progressBar = view.findViewById(R.id.mybidsprogress);

        myBidsViewModel.getMyBids().observe(getViewLifecycleOwner(), new Observer<List<MyBid>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<MyBid> myBids) {
                myBids.sort(new sortTime());
                adapterall = new BidHistoryAdapterAll(getContext(), myBids);
                cartList.setAdapter(adapterall);
            }
        });

        return view;
    }

//    void loadMyBids(final View view) {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/getmybids.php?id=" + SharedPrefManager.getInstance(getActivity()).getUser().getId(),
//                new Response.Listener<String>() {
//                    @RequiresApi(api = Build.VERSION_CODES.N)
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            cartItems.clear();
//                            Log.d("Response", response);
//                            JSONObject obj = new JSONObject(response);
//                            JSONArray heroArray = obj.getJSONArray("Bids");
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
//                            for (int i = 0; i < heroArray.length(); i++) {
//                                JSONObject heroObject = heroArray.getJSONObject(i);
//                                Date a1 = simpleDateFormat.parse(heroObject.getString("endtime"));
//                                PastProducts c = new PastProducts(
//                                        heroObject.getString("past_id"),
//                                        heroObject.getString("image_url"),
//                                        heroObject.getString("title"),
//                                        heroObject.getString("endtime"),
//                                        heroObject.getString("mrp"),
//                                        heroObject.getString("sp"),
//                                        heroObject.getString("description"),
//                                        heroObject.getString("image_url2"),
//                                        heroObject.getString("image_url3"),
//                                        heroObject.getString("firstname"),
//                                        heroObject.getString("bidamount"),
//                                        heroObject.getString("ids"),
//                                        heroObject.getString("orderplaced")
//                                );
//                                if (!(a1.compareTo(new java.sql.Date(System.currentTimeMillis())) > 0))
//                                    cartItems.add(c);
//                            }
//                            cartItems.sort(new sortTime());
//                            adapterall = new BidHistoryAdapterAll(view.getContext(), cartItems);
//                            cartList.setAdapter(adapterall);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
//        requestQueue.add(stringRequest);
//    }

    class sortTime implements Comparator<MyBid> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(MyBid a, MyBid b) {
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

    class BidHistoryAdapterAll extends RecyclerView.Adapter<BidHistoryAdapterAll.BidHistoryViewHolder> {

        Context context;
        List<MyBid> heroList;

        BidHistoryAdapterAll(Context context, List<MyBid> heroList) {
            this.context = context;
            this.heroList = heroList;
        }

        @NonNull
        @Override
        public BidHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BidHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bid_history, parent, false));
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull final BidHistoryViewHolder holder, final int position) {
            if (getItemCount() == 0) {
                progressBar.setVisibility(View.GONE);
            }
            progressBar.setVisibility(View.GONE);
            Glide.with(context)
                    .load(heroList.get(position).getImageUrl())
                    .into(holder.bidHistoryImage);
        }

        @Override
        public int getItemCount() {
            if (heroList.size() == 0) {
                progressBar.setVisibility(View.GONE);
            }
            Log.d("Size", heroList.size() + "");
            return heroList.size();
        }

        class BidHistoryViewHolder extends RecyclerView.ViewHolder {
            ImageView bidHistoryImage;
            TextView bidHistoryTitle;
            TextView bidHistoryStartDate;
            TextView bidHistoryAmount;
            TextView bidHistoryRank;

            BidHistoryViewHolder(View itemView) {
                super(itemView);
                bidHistoryImage = itemView.findViewById(R.id.bidHistoryImage);
                bidHistoryAmount = itemView.findViewById(R.id.bidHistoryAmount);
                bidHistoryTitle = itemView.findViewById(R.id.bidHistoryTitle);
                bidHistoryStartDate = itemView.findViewById(R.id.bidHistoryStartDate);
                bidHistoryRank = itemView.findViewById(R.id.bidHistoryRank);
            }
        }
    }
}
