package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.bidbadnew.Model.Ranking;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductRankAdapter extends RecyclerView.Adapter<ProductRankAdapter.ViewHolder> {

    List<Ranking> rankingList;
    Context context;


    public ProductRankAdapter(List<Ranking> rankingList, Context context) {
        this.rankingList = rankingList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_model, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("ID12", rankingList.get(position).getId());

        if(rankingList.get(position).getId().equals(SharedPrefManager.getInstance(context).getUser().getId().toString())) {
            holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.colorSecondaryDark));
        }
        holder.rank.setText("#"+(position + 1));
        holder.name.setText(rankingList.get(position).getFirstname().substring(0, 1).toUpperCase() + rankingList.get(position).getFirstname().substring(1, rankingList.get(position).getFirstname().length()));
        holder.bidamount.setText(rankingList.get(position).getBidamount());
        try {
            Glide.with(context)
                    .load("http://easyvela.esy.es/AndroidAPI/images/"+rankingList.get(position).getId()+".png")
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(context.getResources().getDrawable(R.drawable.ic_user_male, null))
                    .into(holder.profile);
        } catch (Exception e){
            holder.profile.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_user_male, null));
        }
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView rank, name, bidamount;
        ConstraintLayout constraintLayout;
        CircleImageView profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rankText);
            name = itemView.findViewById(R.id.textName);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            bidamount = itemView.findViewById(R.id.bidamount);
            profile = itemView.findViewById(R.id.image);
        }
    }
}
