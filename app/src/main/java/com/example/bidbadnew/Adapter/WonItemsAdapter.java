package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Fragments.WonBidsFragmentDirections;
import com.example.bidbadnew.Model.WonItem;
import com.example.bidbadnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WonItemsAdapter extends RecyclerView.Adapter<WonItemsAdapter.BidHistoryViewHolder> {

    Context context;
    List<WonItem> heroList;

    public WonItemsAdapter(Context context, List<WonItem> heroList) {
        this.context = context;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public WonItemsAdapter.BidHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WonItemsAdapter.BidHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bid_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BidHistoryViewHolder holder, final int position) {
        holder.bidHistoryTitle.setText(heroList.get(position).getTitle());
        holder.shareBid.setVisibility(View.VISIBLE);
        holder.winnerStats.setVisibility(View.GONE);
        //holder.winnerStats.setText("Stats");
        holder.shareBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_wonbids_to_winnerScreen);
            }
        });
        String pattern = "dd MMM yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Date date = null;
        try {
            date = simpleDateFormat2.parse(heroList.get(position).getEndtime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.bidHistoryMedalImage.setVisibility(View.GONE);
        String dt = simpleDateFormat.format(date);
        holder.bidHistoryStartDate.setText(dt);
        holder.bidHistoryAmount.setText(heroList.get(position).getBidamount());
        Glide.with(context)
                .load(heroList.get(position).getImageUrl())
                .into(holder.bidHistoryImage);

        if (heroList.get(position).getOrderplaced() == null) {
            holder.bidHistoryRank.setVisibility(View.VISIBLE);
            holder.bidHistoryRank.setText("Place order");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(holder.itemView).navigate(WonBidsFragmentDirections.actionNavigationWonbidsToPlaceOrderFragment(null, heroList.get(position)));
                }
            });

            holder.bidHistoryRank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(WonBidsFragmentDirections.actionNavigationWonbidsToPlaceOrderFragment(null, heroList.get(position)));
                }
            });
        } else {
            holder.bidHistoryRank.setText("Order placed");
            holder.bidHistoryRank.setTextColor(ResourcesCompat.getColor(context.getResources(), android.R.color.secondary_text_dark, context.getTheme()));
            // holder.bidHistoryRank.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class BidHistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView bidHistoryImage;
        TextView bidHistoryTitle;
        TextView bidHistoryStartDate;
        TextView bidHistoryAmount;
        TextView bidHistoryRank;
        ImageView bidHistoryMedalImage;
        TextView winnerStats;
        ImageButton shareBid;

        BidHistoryViewHolder(View itemView) {
            super(itemView);
            bidHistoryMedalImage = itemView.findViewById(R.id.bidHistoryMedalImage);
            bidHistoryImage = itemView.findViewById(R.id.bidHistoryImage);
            bidHistoryAmount = itemView.findViewById(R.id.bidHistoryAmount);
            bidHistoryTitle = itemView.findViewById(R.id.bidHistoryTitle);
            bidHistoryStartDate = itemView.findViewById(R.id.bidHistoryStartDate);
            bidHistoryRank = itemView.findViewById(R.id.bidHistoryRank);
            winnerStats = itemView.findViewById(R.id.textView9);
            shareBid = itemView.findViewById(R.id.share_bid);
        }
    }
}