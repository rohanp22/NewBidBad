package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bidbadnew.R;
import com.example.bidbadnew.ui.home.HomeViewModel;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolderCategories> {

    ArrayList<String> categories;
    Context context;

    public CategoriesAdapter(ArrayList<String> categories, Context context, HomeViewModel homeViewModel){
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderCategories onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderCategories(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategories holder, int position) {
        if(position == 0) {
            holder.view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.categories_bg));
            holder.t.setTextColor(context.getResources().getColor(R.color.colorSecondary));
        }
        holder.t.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolderCategories extends RecyclerView.ViewHolder {

        public TextView t;
        View view;

        ViewHolderCategories(View itemView){
            super(itemView);
            t = itemView.findViewById(R.id.text);
            view = itemView.findViewById(R.id.view);
        }

    }
}
