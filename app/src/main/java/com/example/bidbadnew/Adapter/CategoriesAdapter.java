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
import androidx.recyclerview.widget.RecyclerView;

import com.example.bidbadnew.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolderCategories> {

    ArrayList<String> categories;
    Context context;

    public CategoriesAdapter(ArrayList<String> categories, Context context){
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
            holder.t.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_bg));
            holder.t.setBackgroundTintList(context.getResources().getColorStateList(R.color.tintcolor));
            holder.t.setTextColor(Color.parseColor("#ffffff"));
        }
        holder.t.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolderCategories extends RecyclerView.ViewHolder {

        public TextView t;

        ViewHolderCategories(View itemView){
            super(itemView);
            t = itemView.findViewById(R.id.text);
        }

    }
}
